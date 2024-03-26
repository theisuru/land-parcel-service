package com.example.landparcelservice;

import com.example.landparcelservice.exceptions.InvalidRequestException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Collections;

import static org.mockito.Mockito.when;

@WebMvcTest(LandParcelController.class)
public class LandParcelControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private LandParcelService landParcelService;

  @Test
  public void getAllParcelsTest() throws Exception {
    when(landParcelService.getAllLandParcels()).thenReturn(Collections.emptyList());

    mockMvc.perform(MockMvcRequestBuilders.get("/landparcels"))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath("$").isArray())
        .andExpect(MockMvcResultMatchers.jsonPath("$").isEmpty());
  }

  @Test
  public void saveLandParcelTest() throws Exception {
    LandParcel landParcel = getExampleLandParcel();
    when(landParcelService.createLandParcel(Mockito.any())).thenReturn(landParcel);
    String requestBody = new ObjectMapper().writeValueAsString(landParcel);

    mockMvc.perform(MockMvcRequestBuilders.post("/landparcels")
            .contentType(MediaType.APPLICATION_JSON)
            .content(requestBody))
        .andExpect(MockMvcResultMatchers.status().isCreated());
  }

  @Test
  public void invalidDataShouldHaveBadRequestResponse() throws Exception {
    LandParcel landParcel = getInvalidLandParcel();
    when(landParcelService.createLandParcel(landParcel)).thenThrow(new InvalidRequestException("invalid area"));
    String requestBody = new ObjectMapper().writeValueAsString(landParcel);

    mockMvc.perform(MockMvcRequestBuilders.post("/landparcels")
            .contentType(MediaType.APPLICATION_JSON)
            .content(requestBody))
        .andExpect(MockMvcResultMatchers.status().isCreated());
  }

  private LandParcel getExampleLandParcel() {
    return new LandParcel(1L, "test_parcel", LandParcel.Status.SHORT_LISTED, 20.0, true);
  }

  private LandParcel getInvalidLandParcel() {
    return new LandParcel(1L, "test_parcel", LandParcel.Status.SHORT_LISTED, -20.0, true);
  }
}