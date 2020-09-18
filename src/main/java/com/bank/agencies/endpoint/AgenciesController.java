    package com.bank.agencies.endpoint;

    import com.bank.agencies.domain.AgencyGatewayResponse;
    import com.bank.agencies.domain.AgencyResponse;
    import com.bank.agencies.usecase.FindAllAgenciesUseCase;
    import org.springframework.http.HttpStatus;
    import org.springframework.http.MediaType;
    import org.springframework.http.ResponseEntity;
    import org.springframework.web.bind.annotation.*;

    import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

    @RestController
    public class AgenciesController {

        private final FindAllAgenciesUseCase findAllAgenciesUseCase;

        public AgenciesController(FindAllAgenciesUseCase findAllAgenciesUseCase) {
            this.findAllAgenciesUseCase = findAllAgenciesUseCase;
        }

        @GetMapping
        @ResponseStatus(HttpStatus.OK)
        @RequestMapping(value = "/agencies", produces = MediaType.APPLICATION_JSON_VALUE)
        public ResponseEntity<List<AgencyResponse>> findAllAgencies() {

            List<AgencyGatewayResponse> agencies = findAllAgenciesUseCase.execute();

            List<AgencyResponse> agencyResponse = agencies.stream()
                    .map(agencyGateway -> AgencyResponse.AgencyResponseBuilder.anAgencyResponse()
                    .bank(agencyGateway.getBank())
                    .city(agencyGateway.getCity())
                    .name(agencyGateway.getName())
                    .state(agencyGateway.getState()).build())
                    .collect(Collectors.toList());

            return new ResponseEntity<>(agencyResponse, HttpStatus.OK);
        }
        
        @GetMapping
        @ResponseStatus(HttpStatus.OK)
        @RequestMapping(value = "/all_grouped_agencies", produces = MediaType.APPLICATION_JSON_VALUE)
        public ResponseEntity<Map<String, List<AgencyResponse>>> findAllGroupedAgencies() {

            List<AgencyGatewayResponse> agencies = findAllAgenciesUseCase.execute();

            List<AgencyResponse> agencyResponse = agencies.stream()
                    .map(agencyGateway -> AgencyResponse.AgencyResponseBuilder.anAgencyResponse()
                    .bank(agencyGateway.getBank())
                    .city(agencyGateway.getCity())
                    .name(agencyGateway.getName())
                    .state(agencyGateway.getState()).build())
                    .collect(Collectors.toList());
            
            // ordenando por cidades
            agencyResponse.sort((AgencyResponse ar1, AgencyResponse ar2) -> ar1.getCity().compareTo(ar2.getCity()));
            
            // agrupando por estado
            Map<String, List<AgencyResponse>> mapGroupAgencyResponse = agencyResponse.stream()
            		.collect(Collectors.groupingBy(AgencyResponse::getState));

            return new ResponseEntity<>(mapGroupAgencyResponse, HttpStatus.OK);
        }
        
        
        
        /*
         * solução proposta para ganho de performace seria o uso de paginacao
         * buscando de um determinado numero de registros por vez. 
         */
        @ResponseStatus(HttpStatus.OK)
        @GetMapping(value = "{page}/{count}")
        @RequestMapping(value = "/agenciesPages", produces = MediaType.APPLICATION_JSON_VALUE)
        public ResponseEntity<List<AgencyResponse>> findAllAgenciesPages(@PathVariable int page, @PathVariable int count) {

            List<AgencyGatewayResponse> agencies = findAllAgenciesUseCase.execute();

            List<AgencyResponse> agencyResponse = agencies.stream()
                    .map(agencyGateway -> AgencyResponse.AgencyResponseBuilder.anAgencyResponse()
                    .bank(agencyGateway.getBank())
                    .city(agencyGateway.getCity())
                    .name(agencyGateway.getName())
                    .state(agencyGateway.getState()).build())
                    .collect(Collectors.toList());

            return new ResponseEntity<>(agencyResponse, HttpStatus.OK);
        }
    }
