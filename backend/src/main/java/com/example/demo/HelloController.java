package example.demo;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
public class HelloController {

    @CrossOrigin
    @RequestMapping("/api/message")
    public String index() throws JSONException {
        JSONObject jsonObj = new JSONObject();
        jsonObj.put("message","Hello from Java Spring!");
        System.out.println(jsonObj);
        return jsonObj.toString();
    }
}