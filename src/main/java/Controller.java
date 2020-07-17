import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Controller {

    IBANNumberValidator validator = new IBANNumberValidator();

    @FXML
    private TextField filePathField;

    @FXML
    private TextField validatorField;

    @FXML
    private Text batchMessageText;

    @FXML
    private Text validatorText;

    @FXML
    private Rectangle tickLeft;

    @FXML
    private Rectangle tickRight;

    @FXML
    public void showTick() {
        tickLeft.setFill(Color.GREEN);
        tickRight.setFill(Color.GREEN);
    }

    @FXML
    public void hideTick() {
        tickLeft.setFill(Color.valueOf("#b1b1b1"));
        tickRight.setFill(Color.valueOf("#b1b1b1"));
    }

    @FXML
    public void validate() {
        try {
            validator.validateIBAN(validatorField.getText().replace(" ",""));
            validatorText.setText("");
            showTick();
        } catch (Exception e) {
            hideTick();
            validatorText.setText(e.getMessage());
        }
    }

    @FXML
    public void setSelectFile() {
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Text files (*.txt)", "*.txt");
        fileChooser.getExtensionFilters().add(extFilter);
        File file = fileChooser.showOpenDialog(null);
        if (file != null)
        filePathField.setText(file.toString());
    }

    @FXML
    public void processBatchValidation() {
        List<String> lines = new ArrayList<>();

        try {
            lines = Files.readAllLines(Paths.get(filePathField.getText()));
        } catch (Exception e) {
            batchMessageText.setFill(Color.RED);
            batchMessageText.setText(e.getMessage());
        }

        List<String> processedLines = lines.stream()
                .map(line -> {
                    try {
                        line += ";" + validator.validateIBAN(line);
                    } catch (Exception e) {
                        line += ";false";
                    }
                    return line;
                })
                .collect(Collectors.toList());

            String outputPath = "";
            if (!filePathField.toString().equals(""))
            outputPath = filePathField.getText().replaceFirst("\\.txt$", "") + ".out.txt";
        try {
            if (outputPath.equals(".out.txt"))
                throw new RuntimeException("No file selected");
            Files.write(Paths.get(outputPath), processedLines);
            batchMessageText.setFill(Color.GREEN);
            batchMessageText.setText("Output saved to " + outputPath);
        } catch (Exception e) {
            batchMessageText.setFill(Color.RED);
            batchMessageText.setText(e.getMessage());
        } finally {
            filePathField.clear();
        }
    }

}
