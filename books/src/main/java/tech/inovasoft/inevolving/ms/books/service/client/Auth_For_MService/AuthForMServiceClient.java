package tech.inovasoft.inevolving.ms.books.service.client.Auth_For_MService;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import tech.inovasoft.inevolving.ms.books.service.client.Auth_For_MService.dto.AuthenticationRequest;
import tech.inovasoft.inevolving.ms.books.service.client.Auth_For_MService.dto.LoginResponse;

@FeignClient(name = "auth-service", url = "https://api.inevolving.inovasoft.tech/Auth-For-MService/auth/ms/authentication/login")
public interface AuthForMServiceClient {

    @PostMapping("/{microServiceNameReceiver}")
    LoginResponse login(@PathVariable String microServiceNameReceiver, @RequestBody AuthenticationRequest request);

}