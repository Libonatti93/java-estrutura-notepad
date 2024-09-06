import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class SimpleNotepad extends JFrame implements ActionListener {

    private JTextArea textArea;
    private JFileChooser fileChooser;
    private File currentFile;

    public SimpleNotepad() {
        // Configurações da janela principal
        setTitle("Bloco de Notas Simples");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Área de texto
        textArea = new JTextArea();
        add(new JScrollPane(textArea), BorderLayout.CENTER);

        // Menu
        JMenuBar menuBar = new JMenuBar();

        // Menu Arquivo
        JMenu fileMenu = new JMenu("Arquivo");
        JMenuItem newItem = new JMenuItem("Novo");
        JMenuItem openItem = new JMenuItem("Abrir");
        JMenuItem saveItem = new JMenuItem("Salvar");
        JMenuItem saveAsItem = new JMenuItem("Salvar Como");
        JMenuItem exitItem = new JMenuItem("Sair");

        // Adicionando os itens ao menu
        fileMenu.add(newItem);
        fileMenu.add(openItem);
        fileMenu.add(saveItem);
        fileMenu.add(saveAsItem);
        fileMenu.addSeparator();
        fileMenu.add(exitItem);

        // Adicionando Menu Arquivo à barra de menu
        menuBar.add(fileMenu);

        // Adicionando a barra de menu à janela
        setJMenuBar(menuBar);

        // Configuração do JFileChooser
        fileChooser = new JFileChooser();

        // Adicionando ActionListeners aos itens de menu
        newItem.addActionListener(this);
        openItem.addActionListener(this);
        saveItem.addActionListener(this);
        saveAsItem.addActionListener(this);
        exitItem.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();

        switch (command) {
            case "Novo":
                newFile();
                break;
            case "Abrir":
                openFile();
                break;
            case "Salvar":
                saveFile();
                break;
            case "Salvar Como":
                saveFileAs();
                break;
            case "Sair":
                exitNotepad();
                break;
        }
    }

    private void newFile() {
        textArea.setText("");
        currentFile = null;
        setTitle("Bloco de Notas Simples - Novo Arquivo");
    }

    private void openFile() {
        int option = fileChooser.showOpenDialog(this);
        if (option == JFileChooser.APPROVE_OPTION) {
            currentFile = fileChooser.getSelectedFile();
            try (FileReader reader = new FileReader(currentFile)) {
                textArea.read(reader, null);
                setTitle("Bloco de Notas Simples - " + currentFile.getName());
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, "Erro ao abrir o arquivo!", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void saveFile() {
        if (currentFile != null) {
            try (FileWriter writer = new FileWriter(currentFile)) {
                textArea.write(writer);
                JOptionPane.showMessageDialog(this, "Arquivo salvo com sucesso!");
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, "Erro ao salvar o arquivo!", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            saveFileAs();
        }
    }

    private void saveFileAs() {
        int option = fileChooser.showSaveDialog(this);
        if (option == JFileChooser.APPROVE_OPTION) {
            currentFile = fileChooser.getSelectedFile();
            try (FileWriter writer = new FileWriter(currentFile)) {
                textArea.write(writer);
                setTitle("Bloco de Notas Simples - " + currentFile.getName());
                JOptionPane.showMessageDialog(this, "Arquivo salvo com sucesso!");
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, "Erro ao salvar o arquivo!", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void exitNotepad() {
        int confirm = JOptionPane.showConfirmDialog(this, "Deseja sair sem salvar?", "Confirmação", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            dispose();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            SimpleNotepad notepad = new SimpleNotepad();
            notepad.setVisible(true);
        });
    }
}
