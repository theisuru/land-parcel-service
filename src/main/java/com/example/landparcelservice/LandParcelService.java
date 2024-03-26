package com.example.landparcelservice;

import com.example.landparcelservice.exceptions.InvalidRequestException;
import com.example.landparcelservice.exceptions.LandParcelNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class LandParcelService {
  private static final double AREA_MIN = 0;

  private final LandParcelRepository landParcelRepository;

  public LandParcelService(LandParcelRepository landParcelRepository) {
    this.landParcelRepository = landParcelRepository;
  }

  public List<LandParcel> getAllLandParcels() {
    return landParcelRepository.findAll();
  }

  public Optional<LandParcel> getLandParcel(Long objectId) {
    return landParcelRepository.findById(objectId);
  }

  public LandParcel createLandParcel(LandParcel landParcel) {
    validateLandParcel(landParcel);
    validateNullIdForCreate(landParcel.getObjectId());
    return landParcelRepository.save(landParcel);
  }

  public LandParcel updateLandParcel(Long objectId, LandParcel landParcel) {
    validateLandParcel(landParcel);
    validateIdForUpdate(objectId, landParcel);
    return landParcelRepository.save(landParcel);
  }

  public void deleteLandParcel(Long objectId) {
    validateIdExist(objectId);
    landParcelRepository.deleteById(objectId);
  }

  private void validateLandParcel(LandParcel landParcel) {
    validateName(landParcel.getName());
    validateArea(landParcel.getArea());
  }

  private void validateName(String name) {
    if (!StringUtils.hasText(name)) {
      log.warn("Validation failed. LandParcel name must not be empty or null");
      throw new InvalidRequestException("LandParcel name must not be empty or null");
    }
  }

  private void validateArea(double area) {
    if (area < AREA_MIN) {
      log.warn("Validation failed. Land area must be larger than " + AREA_MIN);
      throw new InvalidRequestException("Land area must be larger than " + AREA_MIN);
    }
  }

  private void validateNullIdForCreate(Long id) {
    if (id != null) {
      throw new InvalidRequestException("Can not create LandParcel with existing ID");
    }
  }

  private void validateIdForUpdate(Long id, LandParcel landParcel) {
    validateIdExist(id);
    if (!id.equals(landParcel.getObjectId())) {
      log.warn("Failed to update parcel. Mismatching LandParcel ID in body and URL");
      throw new InvalidRequestException("Mismatching LandParcel ID in body and URL");
    }
  }

  private void validateIdExist(Long id) {
    if (!landParcelRepository.existsById(id)) {
      log.warn("Failed to update parcel. LandParcel ID does not exist");
      throw new LandParcelNotFoundException("LandParcel ID: " + id + " does not exist");
    }
  }
}
