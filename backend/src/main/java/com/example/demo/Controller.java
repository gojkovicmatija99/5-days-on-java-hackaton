package example.demo;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
public class Controller {

    @CrossOrigin
    @RequestMapping("/api/1")
    public String index() throws JSONException {
        return "";
    }


}