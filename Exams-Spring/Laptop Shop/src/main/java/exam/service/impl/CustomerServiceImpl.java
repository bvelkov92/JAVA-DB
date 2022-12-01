package exam.service.impl;

import com.google.gson.Gson;
import exam.model.dto.Customers.CustomerDto;
import exam.model.entity.Customer;
import exam.model.entity.Town;
import exam.repository.CustomerRepository;
import exam.repository.TownRepository;
import exam.service.CustomerService;
import exam.util.ValidationUtils;
import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Optional;

import static exam.constants.Messages.INVALID_CUSTOMER_MSG;
import static exam.constants.Messages.SUCCESSFUL_ADDED_CUSTOMER_MSG;
import static exam.constants.Paths.GET_CUTOMERS_FROM_FILE;

@Service
@Getter
@Setter

public class CustomerServiceImpl  implements CustomerService {

    private final CustomerRepository customerRepository;
    private final ModelMapper modelMapper;
    private final Gson gson;
    private final ValidationUtils validationUtils;
    private final TownRepository townRepository;

    public CustomerServiceImpl(CustomerRepository customerRepository, ModelMapper modelMapper, Gson gson, ValidationUtils validationUtils, TownRepository townRepository) {
        this.customerRepository = customerRepository;
        this.modelMapper = modelMapper;
        this.gson = gson;
        this.validationUtils = validationUtils;
        this.townRepository = townRepository;
    }

    @Override
    public boolean areImported() {
        return this.customerRepository.count()>0;
    }

    @Override
    public String readCustomersFileContent() throws IOException {
        return Files.readString(Path.of(GET_CUTOMERS_FROM_FILE));
    }

    @Override
    public String importCustomers() throws IOException {

        StringBuilder sb = new StringBuilder();

        Arrays.stream(gson.fromJson(readCustomersFileContent(), CustomerDto[].class))
                .forEach(current->{
                    boolean isValid = this.validationUtils.isValid(current);
                    if (this.customerRepository.findByEmail(current.getEmail()).isPresent()){
                        isValid =false;
                    }
                    if(isValid){
                        sb.append(String.format(SUCCESSFUL_ADDED_CUSTOMER_MSG,current.getFirstName(),current.getLastName(), current.getEmail()))
                                .append(System.lineSeparator());

                        Customer customer = modelMapper.map(current, Customer.class);
                        Town town = this.townRepository
                                .findFirstByName(current.getTown().getName()).get();
                        LocalDate registeredOn = ConvertStringToLocalDate(current.getRegisteredOn());
                        customer.setTown(town);
                        customer.setRegisteredOn(registeredOn);
                        this.customerRepository.saveAndFlush(customer);

                    }else {
                        sb.append(INVALID_CUSTOMER_MSG).append(System.lineSeparator());
                    }
                });
        return sb.toString();

    }

    public LocalDate ConvertStringToLocalDate(String date){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/MM/yyyy");
        String dates =  date;

        //convert String to LocalDate
        return  LocalDate.parse(date, formatter);
    }
}
