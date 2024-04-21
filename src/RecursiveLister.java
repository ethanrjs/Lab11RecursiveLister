import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Stream;

public class RecursiveLister extends JFrame {
    private JTextArea textArea;

    public RecursiveLister() {
        createUI();
    }

    private void createUI() {
        setTitle("Recursive Directory Lister");
        setSize(600, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Text area and scroll pane
        textArea = new JTextArea();
        textArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textArea);
        add(scrollPane, BorderLayout.CENTER);

        // Panel for buttons
        JPanel buttonPanel = new JPanel();
        JButton startButton = new JButton("Start");
        JButton quitButton = new JButton("Quit");

        startButton.addActionListener(this::startAction);
        quitButton.addActionListener(e -> System.exit(0));

        buttonPanel.add(startButton);
        buttonPanel.add(quitButton);
        add(buttonPanel, BorderLayout.SOUTH);

        // Title label
        JLabel titleLabel = new JLabel("Select a directory to list all files recursively:");
        add(titleLabel, BorderLayout.NORTH);
    }

    private void startAction(ActionEvent event) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

        int returnValue = fileChooser.showOpenDialog(this);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File selectedDirectory = fileChooser.getSelectedFile();
            textArea.setText(""); // Clear previous text
            listFilesRecursively(selectedDirectory.toPath());
        }
    }

    private void listFilesRecursively(Path path) {
        try (Stream<Path> paths = Files.walk(path)) {
            paths.forEach(p -> textArea.append(p.toString() + "\n"));
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error reading files: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            RecursiveLister ex = new RecursiveLister();
            ex.setVisible(true);
        });
    }
}
