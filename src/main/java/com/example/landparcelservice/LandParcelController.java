package com.example.landparcelservice;

import com.example.landparcelservice.exceptions.LandParcelNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("landparcels")
public class LandParcelController {
  private final LandParcelService landParcelService;

  public LandParcelController(LandParcelService landParcelService) {
    this.landParcelService = landParcelService;
  }

  @GetMapping
  public ResponseEntity<List<LandParcel>> get() {
    return ResponseEntity.ok(landParcelService.getAllLandParcels());
  }

  @GetMapping("/{objectId}")
  public ResponseEntity<LandParcel> get(@PathVariable long objectId) {
    return ResponseEntity.ok(landParcelService.getLandParcel(objectId)
        .orElseThrow(() -> new LandParcelNotFoundException("LandParcel ID does not exist")));
  }

  @PostMapping
  public ResponseEntity<LandParcel> post(@RequestBody LandParcel landParcel) {
    LandParcel landParcelWithId = landParcelService.createLandParcel(landParcel);
    return ResponseEntity.status(HttpStatus.CREATED).body(landParcelWithId);
  }

  @PutMapping("/{objectId}")
  public ResponseEntity<LandParcel> put(@PathVariable long objectId, @RequestBody LandParcel landParcel) {
    LandParcel landParcelWithId = landParcelService.updateLandParcel(objectId, landParcel);
    return ResponseEntity.status(HttpStatus.ACCEPTED).body(landParcelWithId);
  }

  @DeleteMapping("/{objectId}")
  public ResponseEntity<LandParcel> delete(@PathVariable long objectId) {
    landParcelService.deleteLandParcel(objectId);
    return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
  }
}
