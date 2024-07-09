package toy.slick.controller;


import io.swagger.v3.oas.annotations.Parameter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import toy.slick.common.Response;
import toy.slick.interceptor.ApiKeyInterceptor;
import toy.slick.service.CacheService;

@Slf4j
@RestController
@RequestMapping("/api/cache")
public class CacheController {
    private final CacheService cacheService;

    public CacheController(CacheService cacheService) {
        this.cacheService = cacheService;
    }

    @ApiKeyInterceptor.IsAdmin
    @DeleteMapping("/all")
    public Response<HttpStatus> delete() {
        cacheService.delete();

        return new Response<>(HttpStatus.OK);
    }

    @ApiKeyInterceptor.IsAdmin
    @DeleteMapping("/{cacheName}")
    public Response<HttpStatus> delete(@PathVariable @Parameter(example = "_1hour") String cacheName) {
        cacheService.delete(cacheName);

        return new Response<>(HttpStatus.OK);
    }

    @ApiKeyInterceptor.IsAdmin
    @DeleteMapping("/{cacheName}/{objectName}")
    public Response<HttpStatus> delete(@PathVariable @Parameter(example = "_1hour") String cacheName,
                                       @PathVariable @Parameter(example = "ApiKeyService") String objectName) {
        cacheService.delete(cacheName, objectName);

        return new Response<>(HttpStatus.OK);
    }

    @ApiKeyInterceptor.IsAdmin
    @DeleteMapping("/{cacheName}/{objectName}/{methodName}")
    public Response<HttpStatus> delete(@PathVariable @Parameter(example = "_1hour") String cacheName,
                                       @PathVariable @Parameter(example = "ApiKeyService") String objectName,
                                       @PathVariable @Parameter(example = "getApiKey") String methodName) {
        cacheService.delete(cacheName, objectName, methodName);

        return new Response<>(HttpStatus.OK);
    }
}
