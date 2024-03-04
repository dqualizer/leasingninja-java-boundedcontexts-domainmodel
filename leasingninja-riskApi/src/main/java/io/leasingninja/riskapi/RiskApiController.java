package io.leasingninja.riskapi;

import io.leasingninja.riskmanagement.domain.VoteResult;
import io.leasingninja.sales.api.RiskApiResponse;
import io.leasingninja.sales.domain.Amount;
import io.leasingninja.sales.domain.Car;
import io.leasingninja.sales.domain.Contract;
import io.leasingninja.sales.domain.ContractNumber;
import io.leasingninja.sales.domain.Currency;
import io.leasingninja.sales.domain.Customer;
import io.leasingninja.sales.ui.ContractModel;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class RiskApiController {

    private RiskApiService riskApiService;

    public RiskApiController(RiskApiService riskApiService) {
        this.riskApiService = riskApiService;
    }

    @PostMapping(value = "/vote", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public RiskApiResponse voteContractAutomated(@RequestBody ContractModel contractDto){

        System.out.println("test test test");
        Contract contract = contractDtoToContract(contractDto);
        VoteResult isContractVoted = riskApiService.calculateVoteResult(contract);

        RiskApiResponse votedResultResponse = new RiskApiResponse(contract.getNumber().toInt(), isContractVoted.toString());

        return votedResultResponse;

    }



    private Contract contractDtoToContract(ContractModel contractDto){

        ContractNumber contractNumber = ContractNumber.of(contractDto.number);
        Customer customer = Customer.of(contractDto.lessee);
        Car car = Car.of(contractDto.car);
        Amount price = Amount.of(contractDto
            .price_amount, Currency.valueOf(contractDto.price_currency));

        return new Contract(contractNumber, customer, car, price);
    }
}
