package View;

import Model.Control;
import Model.IO_for_Client;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;
import java.io.IOException;

public class LoginController {

    public TextField nameTextField;
    public TextField passwordTextField;

    public void loginButtionClicked(ActionEvent actionEvent) throws IOException, ParserConfigurationException, SAXException, XPathExpressionException {

        String name = nameTextField.getText();
        String password = passwordTextField.getText();
        Model.Control con = new Control();
       if(true){

           FXMLLoader loader = new FXMLLoader();
           loader.setLocation(getClass().getResource("ClientMainScene.fxml"));
           Parent afterLoginParent = loader.load();
           Scene afterLoginScene = new Scene(afterLoginParent);
           Stage window = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
           window.setScene(afterLoginScene);
           ClientMainSceneController controller = loader.getController();
           afterLoginScene.setUserData(controller);
           controller.client = con.ReadClient(name);
           controller.buildScene();
           controller.id = name;
           //System.out.println(controller.client.getName());
           window.show();
       }

    }
}
