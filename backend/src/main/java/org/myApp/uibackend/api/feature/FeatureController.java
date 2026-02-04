package org.myApp.uibackend.api.feature;

import org.myApp.uibackend.domain.feature.FeatureFlag;
import org.myApp.uibackend.domain.feature.FeatureState;
import org.myApp.uibackend.service.FeatureService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * - REST controller exposing feature toggle API.
 * - Static frontend polls this endpoint.
 * - Selenium tests validate async correctness.
 */
@RestController
@RequestMapping("/api/features")
public class FeatureController {

    private final FeatureService service;

    public FeatureController(FeatureService service) {
        this.service = service;
    }


    // Dashboard polling endpoint.
    // UI repeatedly calls this until propagation completes.
    @GetMapping
    public Map<FeatureFlag, FeatureState> snapshot(){
        return service.snapshot();
    }

    // Toggle endpoint
    // UI requests change, backend responds immediately, but applies update asynchronously later.
    @PostMapping("/{flag}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void toggle(@PathVariable("flag") String flag){

        FeatureFlag featureFlag = FeatureFlag.valueOf(flag);

        boolean currentState = service
                .snapshot()
                .get(featureFlag)
                .enabled();

        service.requestChange(featureFlag, !currentState);
    }
}
