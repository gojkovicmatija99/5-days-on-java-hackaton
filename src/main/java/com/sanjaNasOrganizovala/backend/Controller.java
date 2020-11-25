package main.java.com.sanjaNasOrganizovala.backend;

import main.java.com.sanjaNasOrganizovala.backend.Model.DataRepository;
import main.java.com.sanjaNasOrganizovala.backend.Model.DataRepositoryImplementation;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.*;

@RestController
public class Controller {

    DataRepository dataRepository = DataRepositoryImplementation.getInstance();

    @CrossOrigin
    @GetMapping("/api/1")
    public String index1() throws JSONException {
        return dataRepository.getQuery1();
    }


    @CrossOrigin
    @RequestMapping(value = "/api/2/{id}")
    public String index2(@PathVariable Long id) throws JSONException {
        return dataRepository.getQuery2(id);
    }

    @CrossOrigin
    @RequestMapping(value = "/api/3/{id}")
    public String index3(@PathVariable Long id) throws JSONException {
        return dataRepository.getQuery3(id);
    }


    //@CrossOrigin
    @GetMapping("/api/4")
    public String index4() throws JSONException {
        return dataRepository.getQuery4();
    }

    //@CrossOrigin
    @GetMapping("/api/5")
    public String index5() throws JSONException {
        return dataRepository.getQuery5();
    }

    //@CrossOrigin
    @GetMapping("/api/6")
    public String index6() throws JSONException {
        return dataRepository.getQuery6();
    }


}