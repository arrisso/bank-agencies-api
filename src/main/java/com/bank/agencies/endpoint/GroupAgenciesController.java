    package com.bank.agencies.endpoint;

    import com.bank.agencies.domain.AgencyGatewayResponse;
    import com.bank.agencies.domain.AgencyResponse;
    import com.bank.agencies.usecase.FindAllAgenciesUseCase;
    import org.springframework.http.HttpStatus;
    import org.springframework.http.MediaType;
    import org.springframework.http.ResponseEntity;
    import org.springframework.web.bind.annotation.*;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

    @RestController
    @RequestMapping(value = "/groupOfAgencies", produces = MediaType.APPLICATION_JSON_VALUE)
    public class GroupAgenciesController {

        private final FindAllAgenciesUseCase findAllAgenciesUseCase;

        public GroupAgenciesController(FindAllAgenciesUseCase findAllAgenciesUseCase) {
            this.findAllAgenciesUseCase = findAllAgenciesUseCase;
        }

        @GetMapping
        @ResponseStatus(HttpStatus.OK)
        public ResponseEntity<Map<String, List<AgencyResponse>>> findAllAgencies(@RequestParam(required = false, name="state") String state) {

            List<AgencyGatewayResponse> agencies = findAllAgenciesUseCase.execute();

            List<AgencyResponse> agencyResponse = agencies.stream()
                    .map(agencyGateway -> AgencyResponse.AgencyResponseBuilder.anAgencyResponse()
                    .bank(agencyGateway.getBank())
                    .city(agencyGateway.getCity())
                    .name(agencyGateway.getName())
                    .state(agencyGateway.getState()).build())
                    .collect(Collectors.toList());
            
            agencyResponse.sort((AgencyResponse ar1, AgencyResponse ar2) -> ar1.getCity().compareTo(ar2.getCity()));
            
            Map<String, List<AgencyResponse>> mapGroupAgencyResponse = agencyResponse.stream()
            		.collect(Collectors.groupingBy(AgencyResponse::getState));

            return new ResponseEntity<>(mapGroupAgencyResponse, HttpStatus.OK);
        }
    }
