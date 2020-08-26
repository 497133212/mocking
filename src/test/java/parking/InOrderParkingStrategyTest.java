package parking;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.Collections;

import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;
import static org.junit.Assert.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static parking.ParkingStrategy.NO_PARKING_LOT;


public class InOrderParkingStrategyTest {


    private InOrderParkingStrategy inOrderParkingStrategy;

    @Before
    public void setUp() {
        this.inOrderParkingStrategy = new InOrderParkingStrategy();
    }

    @Test
    public void testCreateReceipt_givenACarAndAParkingLog_thenGiveAReceiptWithCarNameAndParkingLotName() throws Exception {
        ParkingLot mockParkingLot = Mockito.mock(ParkingLot.class);
        Car mockCar = Mockito.mock(Car.class);
        given(mockParkingLot.getName()).willReturn("parkingLot");
        given(mockParkingLot.getMaxCapacity()).willReturn(10);
        given(mockCar.getName()).willReturn("car");

        Receipt receipt = inOrderParkingStrategy.createReceipt(mockParkingLot, mockCar);
        assertEquals("parkingLot", receipt.getParkingLotName());
        assertEquals("car", receipt.getCarName());
    }

    @Test
    public void testCreateNoSpaceReceipt_givenACar_thenGiveANoSpaceReceipt() {
        Car mockCar = Mockito.mock(Car.class);
        given(mockCar.getName()).willReturn("car");

        Receipt receipt = inOrderParkingStrategy.createNoSpaceReceipt(mockCar);
        assertEquals(NO_PARKING_LOT, receipt.getParkingLotName());
        assertEquals("car", receipt.getCarName());

    }

    @Test
    public void testPark_givenNoAvailableParkingLot_thenCreateNoSpaceReceipt() {
        InOrderParkingStrategy mockInOrderParkingStrategy = spy(inOrderParkingStrategy);
        ParkingLot mockParkingLot = Mockito.mock(ParkingLot.class);
        given(mockParkingLot.isFull()).willReturn(true);
        Car car = mock(Car.class);
        given(car.getName()).willReturn("car");
        mockInOrderParkingStrategy.park(singletonList(mockParkingLot),car);
        verify(mockInOrderParkingStrategy, times(1)).createNoSpaceReceipt(car);
    }

    @Test
    public void testPark_givenThereIsOneParkingLotWithSpace_thenCreateReceipt() {
        InOrderParkingStrategy mockInOrderParkingStrategy = spy(inOrderParkingStrategy);
        ParkingLot mockParkingLot = Mockito.mock(ParkingLot.class);
        given(mockParkingLot.isFull()).willReturn(false);
        Car car = mock(Car.class);
        given(car.getName()).willReturn("car");
        mockInOrderParkingStrategy.park(singletonList(mockParkingLot),car);
        verify(mockInOrderParkingStrategy, times(1)).createReceipt(mockParkingLot,car);
    }

    @Test
    public void testPark_givenThereIsOneFullParkingLot_thenCreateReceipt() {
        InOrderParkingStrategy mockInOrderParkingStrategy = spy(inOrderParkingStrategy);
        ParkingLot mockParkingLot1 = Mockito.mock(ParkingLot.class);
        given(mockParkingLot1.isFull()).willReturn(true);
        ParkingLot mockParkingLot2 = Mockito.mock(ParkingLot.class);
        given(mockParkingLot2.isFull()).willReturn(false);
        Car car = mock(Car.class);
        given(car.getName()).willReturn("car");
        mockInOrderParkingStrategy.park(asList(mockParkingLot1,mockParkingLot2),car);
        verify(mockInOrderParkingStrategy, times(1)).createReceipt(mockParkingLot2,car);
    }

    @Test
    public void testPark_givenThereIsMultipleParkingLotAndFirstOneIsFull_thenCreateReceiptWithUnfullParkingLot() {

        /* Exercise 3: Test park() method. Use Mockito.spy and Mockito.verify to test the situation for multiple parking lot situation */

    }


}
