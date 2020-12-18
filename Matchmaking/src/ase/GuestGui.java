package ase;

// import com.sun.tools.javac.util.StringUtils;
import jade.core.AID;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.UtilDateModel;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import java.util.stream.IntStream;

public class GuestGui {

    JFrame jFrame;
    DefaultListModel<String> providersList;

    List<Provider> currentProviders = new ArrayList<>();

    public GuestGui(Set<AID> providers) {
        System.out.println("number of providers: ");
        System.out.println(providers.size());

        jFrame = new JFrame("Welcome Guest user");
        jFrame.setSize(1000, 600);

        JPanel jPanel = new JPanel();
        jPanel.setLayout(new BorderLayout());

        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BorderLayout());
        leftPanel.setSize(1000, 600);

        providersList = new DefaultListModel<>();
        for (AID provider : providers) {
            Provider provider1 = UserManagerAgent.getProvider(provider.getLocalName().split(":")[1]);
            currentProviders.add(provider1);
            String text = provider1.getInfo();
            providersList.addElement(text);
        }
        JList<String> list = new JList<>(providersList);
        JPanel providerPanel = new JPanel();
        providerPanel.setLayout(new BorderLayout());
        providerPanel.add(new JLabel("Providers:"),BorderLayout.NORTH);
        providerPanel.add(list,BorderLayout.CENTER);
        leftPanel.add(providerPanel, BorderLayout.SOUTH);

        HintTextField searchTextField = new HintTextField("Search Provider");
        searchTextField.setSize(new Dimension(200, 24));
        leftPanel.add(searchTextField, BorderLayout.NORTH);
        searchTextField.getDocument().addDocumentListener(new DocumentListener() {
            public void changedUpdate(DocumentEvent e) {
                searchProviders(e);
            }

            public void removeUpdate(DocumentEvent e) {
                searchProviders(e);
            }

            public void insertUpdate(DocumentEvent e) {
                searchProviders(e);
            }

            private void searchProviders(DocumentEvent e) {
                String searchedText = searchTextField.getText();
                if (searchedText.isEmpty()) {
                    providersList.removeAllElements();
                    for (Provider provider : currentProviders) {
                        providersList.addElement(provider.getInfo());
                    }
                } else {
                    providersList.removeAllElements();
                    List<Provider> searchedProviders = UserManagerAgent.searchProvider(searchedText, currentProviders);
                    for (Provider provider : searchedProviders) {
                        providersList.addElement(provider.getInfo());
                    }
                }
            }
        });


        jPanel.add(leftPanel, BorderLayout.WEST);

        
        jFrame.add(jPanel);
    }

    public void showGui() {
        jFrame.setVisible(true);
    }

    public void dispose() {
        this.jFrame.dispose();
    }

}
