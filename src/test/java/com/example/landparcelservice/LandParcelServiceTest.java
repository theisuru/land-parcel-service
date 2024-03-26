package com.example.landparcelservice;

import com.example.landparcelservice.exceptions.InvalidRequestException;
import com.example.landparcelservice.exceptions.LandParcelNotFoundException;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@SpringBootTest
public class LandParcelServiceTest {

  @Mock
  private LandParcelRepository landParcelRepository;

  @InjectMocks
  private LandParcelService landParcelService;

  @Test
  public void testGetAllParcels() {
    when(landParcelRepository.findAll()).thenReturn(Collections.emptyList());
    List<LandParcel> parcels = landParcelService.getAllLandParcels();
    assertEquals(0, parcels.size());
  }

  @Test
  public void testGetParcelById() {
    long parcelId = 1L;
    LandParcel parcel = new LandParcel(parcelId, "test_land", LandParcel.Status.SHORT_LISTED, 45.5, false);
    when(landParcelRepository.findById(parcelId)).thenReturn(Optional.of(parcel));
    Optional<LandParcel> result = landParcelService.getLandParcel(parcelId);
    assertEquals(parcelId, result.orElseThrow().getObjectId());
  }

  @Test
  public void testCreateParcel() {
    LandParcel parcel = new LandParcel(null, "test_land", LandParcel.Status.SHORT_LISTED, 45.5, false);
    when(landParcelRepository.save(parcel)).thenReturn(parcel);
    LandParcel result = landParcelService.createLandParcel(parcel);
    assertEquals(parcel.getName(), result.getName());
  }

  @Test
  public void testUpdateParcel() {
    long parcelId = 1L;
    LandParcel parcel = new LandParcel(parcelId, "test_land", LandParcel.Status.SHORT_LISTED, 45.5, false);
    when(landParcelRepository.save(parcel)).thenReturn(parcel);
    when(landParcelRepository.existsById(parcelId)).thenReturn(true);
    LandParcel result = landParcelService.updateLandParcel(parcelId, parcel);
    assertEquals(parcel.getName(), result.getName());
  }


  // depending on the context we could add more validation logic and test cases. Below is a few example unit tests
  // for validation failures.

  @Test
  public void createLandParcelWithIdShouldThrowException() {
    long parcelId = 1L;
    LandParcel parcel = new LandParcel(parcelId, "test_land", LandParcel.Status.SHORT_LISTED, 45.5, false);
    when(landParcelRepository.save(parcel)).thenReturn(parcel);
    assertThrows(InvalidRequestException.class, () -> landParcelService.createLandParcel(parcel));
  }

  @Test
  public void nonExistingIdsShouldThrowException() {
    long parcelId = 1L;
    doNothing().when(landParcelRepository).deleteById(parcelId);
    when(landParcelRepository.existsById(parcelId)).thenReturn(false);
    assertThrows(LandParcelNotFoundException.class, () -> landParcelService.deleteLandParcel(parcelId));
  }

  @Test
  public void emptyNameShouldThrowException() {
    LandParcel parcel = new LandParcel(1L, "   ", LandParcel.Status.SHORT_LISTED, 45.5, false);
    when(landParcelRepository.save(parcel)).thenReturn(parcel);
    Exception exception = assertThrows(InvalidRequestException.class, () -> landParcelService.createLandParcel(parcel));
    assertEquals("LandParcel name must not be empty or null", exception.getMessage());
  }

  @Test
  public void negativeLandAreaShouldThrowException() {
    LandParcel parcel = new LandParcel(1L, "test_land", LandParcel.Status.SHORT_LISTED, -45.5, false);
    when(landParcelRepository.save(parcel)).thenReturn(parcel);
    assertThrows(InvalidRequestException.class, () -> landParcelService.createLandParcel(parcel));
  }

}