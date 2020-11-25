package example.demo;

import example.demo.Model.DataRepository;
import example.demo.Model.DataRepositoryImplementation;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
public class Controller {

    DataRepository dataRepository = DataRepositoryImplementation.getInstance();

    //@CrossOrigin
    @GetMapping("/api/1")
    public String index1() throws JSONException {
        return dataRepository.getQuery1();
    }

    /*
    @CrossOrigin
    @RequestMapping("/api/2")
    public String index2() throws JSONException {
        return dataRepository.getQuery2();
    }

    @CrossOrigin
    @RequestMapping("/api/3")
    public String index3() throws JSONException {
        return dataRepository.getQuery3();
    }
     */

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