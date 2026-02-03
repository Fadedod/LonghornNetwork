import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;

public class LongHornGui extends JFrame {
    private JComboBox<String> caseDropdown;
    private JButton executeButton;
    private JTextArea outputDisplay;
    private NetworkVisualizer networkVis;
    private JTextArea pairingsDisplay;
    private JComboBox<String> studentPicker; 
    private JTextField companyInput;
    private JTextArea pathDisplay;
    
    // Components for the History Tab
    private JComboBox<String> historyCaseSelector;
    private JComboBox<String> historyStudentSelector;
    private JTextArea historyDisplay;

    private List<List<UniversityStudent>> allTestData;
    private static final Color BURNT_ORANGE = new Color(191, 87, 0);

    public LongHornGui() {
        super("Longhorn Network Lab UI");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(950, 750);
        setLocationRelativeTo(null);

        // Load data from Main.java
        allTestData = Arrays.asList(
                Main.generateTestCase1(),
                Main.generateTestCase2(),
                Main.generateTestCase3()
        );

        try {
            setIconImage(new ImageIcon("src/longhorn.png").getImage());
        } catch (Exception ignored) {}
        
        configureUITheme();

        JTabbedPane tabContainer = new JTabbedPane();
        tabContainer.setBackground(Color.WHITE);
        tabContainer.setForeground(BURNT_ORANGE);
        tabContainer.setFont(new Font("Arial", Font.BOLD, 14));

        tabContainer.addTab("Test Runner", buildTestPanel());
        tabContainer.addTab("Graph Viewer", buildGraphPanel());
        tabContainer.addTab("Roommate Pairs", buildPairingPanel());
        tabContainer.addTab("Referral Path", buildPathPanel());
        tabContainer.addTab("Student History", buildHistoryPanel());

        add(tabContainer);
    }

    private void configureUITheme() {
        UIManager.put("ComboBox.selectionBackground", BURNT_ORANGE);
        UIManager.put("ComboBox.selectionForeground", Color.WHITE);
        UIManager.put("ComboBox.background", Color.WHITE);
        UIManager.put("ComboBox.foreground", Color.BLACK);
        UIManager.put("Button.background", BURNT_ORANGE);
        UIManager.put("Button.foreground", Color.WHITE);
        UIManager.put("Panel.background", Color.WHITE);
    }

    // --- Tab 1: Test Runner ---
    private JPanel buildTestPanel() {
        JPanel container = new JPanel(new BorderLayout());
        JPanel controlBar = new JPanel();

        caseDropdown = new JComboBox<>(new String[]{"Test Case 1", "Test Case 2", "Test Case 3", "All Test Cases"});
        executeButton = new JButton("Run Tests");
        applyButtonStyle(executeButton);
        executeButton.addActionListener(e -> executeTests());

        controlBar.add(new JLabel("Select Test Case:"));
        controlBar.add(caseDropdown);
        controlBar.add(executeButton);
        container.add(controlBar, BorderLayout.NORTH);

        JPanel contentArea = new JPanel(new BorderLayout());
        outputDisplay = new JTextArea(10, 50);
        outputDisplay.setEditable(false);
        outputDisplay.setFont(new Font("Monospaced", Font.PLAIN, 12));
        JScrollPane scroller = new JScrollPane(outputDisplay);
        contentArea.add(scroller, BorderLayout.CENTER);

        container.add(contentArea, BorderLayout.CENTER);
        return container;
    }

    // --- Tab 2: Graph Viewer ---
    private JPanel buildGraphPanel() {
        JPanel container = new JPanel(new BorderLayout());
        
        JPanel controlBar = new JPanel();
        JComboBox<String> dataSelector = new JComboBox<>(new String[]{"Test Case 1", "Test Case 2", "Test Case 3"});
        JButton loadBtn = new JButton("Load Graph");
        applyButtonStyle(loadBtn);
        
        loadBtn.addActionListener(e -> {
            int selection = dataSelector.getSelectedIndex();
            List<UniversityStudent> dataset = allTestData.get(selection);
            StudentGraph network = new StudentGraph(dataset);
            networkVis.renderNetwork(network, dataset);
        });

        controlBar.add(new JLabel("Select Data:"));
        controlBar.add(dataSelector);
        controlBar.add(loadBtn);
        container.add(controlBar, BorderLayout.NORTH);

        networkVis = new NetworkVisualizer();
        container.add(networkVis, BorderLayout.CENTER);

        return container;
    }

    // --- Tab 3: Roommate Pairs ---
    private JPanel buildPairingPanel() {
        JPanel container = new JPanel(new BorderLayout());
        
        JPanel controlBar = new JPanel();
        JComboBox<String> dataSelector = new JComboBox<>(new String[]{"Test Case 1", "Test Case 2", "Test Case 3"});
        JButton processBtn = new JButton("Compute Roommates");
        applyButtonStyle(processBtn);
        
        processBtn.addActionListener(e -> {
            int selection = dataSelector.getSelectedIndex();
            List<UniversityStudent> dataset = allTestData.get(selection);
            dataset.forEach(student -> student.setRoommate(null));
            GaleShapley.assignRoommates(dataset);
            
            StringBuilder result = new StringBuilder();
            result.append("Roommate Assignments:\n---------------------\n");
            boolean anyFound = false;
            for (UniversityStudent student : dataset) {
                if (student.getRoommate() != null && student.getName().compareTo(student.getRoommate().getName()) < 0) {
                    result.append(student.getName()).append(" <---> ").append(student.getRoommate().getName()).append("\n");
                    anyFound = true;
                }
            }
            if (!anyFound) result.append("No matches found.");
            pairingsDisplay.setText(result.toString());
        });

        controlBar.add(new JLabel("Select Data:"));
        controlBar.add(dataSelector);
        controlBar.add(processBtn);
        container.add(controlBar, BorderLayout.NORTH);

        pairingsDisplay = new JTextArea();
        pairingsDisplay.setEditable(false);
        pairingsDisplay.setFont(new Font("Arial", Font.PLAIN, 16));
        container.add(new JScrollPane(pairingsDisplay), BorderLayout.CENTER);

        return container;
    }

    // --- Tab 4: Referral Path ---
    private JPanel buildPathPanel() {
        JPanel container = new JPanel(new BorderLayout());

        JPanel controlBar = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JComboBox<String> dataSelector = new JComboBox<>(new String[]{"Test Case 1", "Test Case 2", "Test Case 3"});
        studentPicker = new JComboBox<>();
        companyInput = new JTextField("Google", 10);
        JButton searchBtn = new JButton("Find Path");
        applyButtonStyle(searchBtn);

        controlBar.add(new JLabel("Data:"));
        controlBar.add(dataSelector);
        controlBar.add(new JLabel("Start Student:"));
        controlBar.add(studentPicker);
        controlBar.add(new JLabel("Target Company:"));
        controlBar.add(companyInput);
        controlBar.add(searchBtn);
        container.add(controlBar, BorderLayout.NORTH);

        JPanel contentArea = new JPanel(new BorderLayout());
        JPanel textSection = new JPanel(new BorderLayout());
        pathDisplay = new JTextArea(2, 50);
        pathDisplay.setEditable(false);
        pathDisplay.setFont(new Font("Arial", Font.BOLD, 14));
        pathDisplay.setBackground(new Color(245, 245, 245));
        textSection.add(new JScrollPane(pathDisplay), BorderLayout.CENTER);

        PathVisualizer pathVis = new PathVisualizer();
        contentArea.add(textSection, BorderLayout.NORTH);
        contentArea.add(pathVis, BorderLayout.CENTER);
        container.add(contentArea, BorderLayout.CENTER);

        dataSelector.addActionListener(e -> {
            int selection = dataSelector.getSelectedIndex();
            List<UniversityStudent> dataset = allTestData.get(selection);
            studentPicker.removeAllItems();
            dataset.forEach(student -> studentPicker.addItem(student.getName()));
        });
        dataSelector.setSelectedIndex(0);

        searchBtn.addActionListener(e -> {
            int selection = dataSelector.getSelectedIndex();
            List<UniversityStudent> dataset = allTestData.get(selection);
            String chosenName = (String) studentPicker.getSelectedItem();
            UniversityStudent origin = dataset.stream()
                    .filter(student -> student.getName().equals(chosenName))
                    .findFirst()
                    .orElse(null);
            String targetFirm = companyInput.getText().trim();
            
            if (origin != null && !targetFirm.isEmpty()) {
                StudentGraph network = new StudentGraph(dataset);
                ReferralPathFinder pathfinder = new ReferralPathFinder(network);
                List<UniversityStudent> route = pathfinder.findReferralPath(origin, targetFirm);

                StringBuilder output = new StringBuilder();
                if (route.isEmpty()) {
                    output.append("No path found to ").append(targetFirm);
                } else {
                    route.forEach(student -> output.append(student.getName()).append(" -> "));
                    output.setLength(output.length() - 4);
                }
                pathDisplay.setText(output.toString());
                pathVis.displayPath(route);
            }
        });

        return container;
    }

    // --- Tab 5: Student History (UPDATED FOR YOUR CODE) ---
    private JPanel buildHistoryPanel() {
        JPanel container = new JPanel(new BorderLayout());

        JPanel controlBar = new JPanel();
        historyCaseSelector = new JComboBox<>(new String[]{"Test Case 1", "Test Case 2", "Test Case 3"});
        historyStudentSelector = new JComboBox<>();
        JButton viewHistoryBtn = new JButton("View History");
        applyButtonStyle(viewHistoryBtn);

        controlBar.add(new JLabel("Select Data:"));
        controlBar.add(historyCaseSelector);
        controlBar.add(new JLabel("Select Student:"));
        controlBar.add(historyStudentSelector);
        controlBar.add(viewHistoryBtn);
        container.add(controlBar, BorderLayout.NORTH);

        historyDisplay = new JTextArea();
        historyDisplay.setEditable(false);
        historyDisplay.setFont(new Font("Monospaced", Font.PLAIN, 14));
        container.add(new JScrollPane(historyDisplay), BorderLayout.CENTER);

        historyCaseSelector.addActionListener(e -> {
            int selection = historyCaseSelector.getSelectedIndex();
            List<UniversityStudent> dataset = allTestData.get(selection);
            historyStudentSelector.removeAllItems();
            dataset.forEach(s -> historyStudentSelector.addItem(s.getName()));
            historyDisplay.setText("");
        });
        
        historyCaseSelector.setSelectedIndex(0);

        viewHistoryBtn.addActionListener(e -> {
            String studentName = (String) historyStudentSelector.getSelectedItem();
            int selection = historyCaseSelector.getSelectedIndex();
            List<UniversityStudent> dataset = allTestData.get(selection);

            UniversityStudent s = dataset.stream()
                    .filter(student -> student.getName().equals(studentName))
                    .findFirst().orElse(null);

            if (s != null) {
                StringBuilder sb = new StringBuilder();
                sb.append("History for Student: ").append(s.getName()).append("\n");
                sb.append("========================================\n\n");

                // 1. Friend Requests (Using String List)
                sb.append("[Friend List]\n");
                if (s.getFriends().isEmpty()) {
                    sb.append("None\n");
                } else {
                    // Corrected to use String iteration
                    for (String friendName : s.getFriends()) {
                        sb.append("- ").append(friendName).append("\n");
                    }
                }
                sb.append("\n");

                // 2. Chat History (Using Map<String, List<String>>)
                sb.append("[Chat History]\n");
                if (s.getChatHistories().isEmpty()) {
                    sb.append("None\n");
                } else {
                    // Corrected to use String keys
                    for (Map.Entry<String, List<String>> entry : s.getChatHistories().entrySet()) {
                        sb.append("Conversation with ").append(entry.getKey()).append(":\n");
                        for (String msg : entry.getValue()) {
                            sb.append("  ").append(msg).append("\n");
                        }
                    }
                }
                historyDisplay.setText(sb.toString());
            }
        });

        return container;
    }

    private void executeTests() {
        outputDisplay.setText("");
        String choice = (String) caseDropdown.getSelectedItem();
        if (choice.equals("All Test Cases")) {
            for (int i = 1; i <= allTestData.size(); i++) processTestCase(i);
        } else {
            int caseNumber = Integer.parseInt(choice.split(" ")[2]);
            processTestCase(caseNumber);
        }
    }

    private void processTestCase(int num) {
        outputDisplay.append("=== Test Case " + num + " ===\n");
        List<UniversityStudent> dataset = allTestData.get(num - 1);
        dataset.forEach(student -> outputDisplay.append(student.toString() + "\n"));
        outputDisplay.append("\n");
        int score = Main.gradeLab(dataset, num); 
        outputDisplay.append("Test Case " + num + " Final Score: " + score + "\n\n");
    }

    private void applyButtonStyle(JButton btn) {
        btn.setBackground(BURNT_ORANGE);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setFont(new Font("Arial", Font.BOLD, 12));
    }

    private static class NetworkVisualizer extends JPanel {
        private StudentGraph network;
        private List<UniversityStudent> studentList;

        void renderNetwork(StudentGraph net, List<UniversityStudent> students) {
            this.network = net;
            this.studentList = students;
            repaint();
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (network == null || studentList == null) return;

            Graphics2D g2d = (Graphics2D) g;
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            int w = getWidth(), h = getHeight();
            int radius = Math.min(w, h) / 3;
            int centerX = w / 2, centerY = h / 2;
            Map<UniversityStudent, Point> positions = new HashMap<>();
            int total = studentList.size();

            for (int i = 0; i < total; i++) {
                double theta = 2 * Math.PI * i / total;
                int px = centerX + (int) (radius * Math.cos(theta));
                int py = centerY + (int) (radius * Math.sin(theta));
                positions.put(studentList.get(i), new Point(px, py));
            }

            g2d.setStroke(new BasicStroke(1));
            g2d.setColor(Color.LIGHT_GRAY);
            for (UniversityStudent s : studentList) {
                for (StudentGraph.Edge edge : network.getNeighbors(s)) {
                    UniversityStudent neighbor = edge.neighbor;
                    if (studentList.indexOf(neighbor) <= studentList.indexOf(s)) continue;
                    Point p1 = positions.get(s), p2 = positions.get(neighbor);
                    g2d.drawLine(p1.x, p1.y, p2.x, p2.y);
                    
                    g2d.setColor(Color.BLACK);
                    int midX = (p1.x + p2.x) / 2, midY = (p1.y + p2.y) / 2;
                    g2d.drawString(String.valueOf(edge.weight), midX, midY);
                    g2d.setColor(Color.LIGHT_GRAY);
                }
            }

            g2d.setColor(BURNT_ORANGE);
            g2d.setStroke(new BasicStroke(3));
            for (UniversityStudent s : studentList) {
                UniversityStudent partner = s.getRoommate();
                if (partner != null && studentList.contains(partner)) {
                    if (s.getName().compareTo(partner.getName()) < 0) {
                        Point p1 = positions.get(s);
                        Point p2 = positions.get(partner);
                        g2d.drawLine(p1.x, p1.y, p2.x, p2.y);
                    }
                }
            }

            for (UniversityStudent s : studentList) {
                Point pos = positions.get(s);
                g2d.setColor(Color.WHITE);
                g2d.fillOval(pos.x - 20, pos.y - 20, 40, 40);
                g2d.setColor(BURNT_ORANGE);
                g2d.setStroke(new BasicStroke(2));
                g2d.drawOval(pos.x - 20, pos.y - 20, 40, 40);
                
                g2d.setColor(Color.BLACK);
                g2d.setFont(new Font("Arial", Font.BOLD, 12));
                FontMetrics metrics = g2d.getFontMetrics();
                int labelWidth = metrics.stringWidth(s.getName());
                g2d.drawString(s.getName(), pos.x - labelWidth / 2, pos.y + 5);
            }
        }
    }

    private static class PathVisualizer extends JPanel {
        private List<UniversityStudent> pathRoute;

        void displayPath(List<UniversityStudent> route) {
            this.pathRoute = route;
            repaint();
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (pathRoute == null || pathRoute.isEmpty()) return;

            Graphics2D g2d = (Graphics2D) g;
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            
            int panelWidth = getWidth();
            int panelHeight = getHeight();
            int spacing = panelWidth / (pathRoute.size() + 1);

            for (int i = 0; i < pathRoute.size(); i++) {
                int xPos = (i + 1) * spacing;
                int yPos = panelHeight / 2;

                if (i < pathRoute.size() - 1) {
                    int nextX = (i + 2) * spacing;
                    g2d.setColor(Color.BLACK);
                    g2d.setStroke(new BasicStroke(2));
                    g2d.drawLine(xPos + 20, yPos, nextX - 20, yPos);
                }

                g2d.setColor(BURNT_ORANGE);
                g2d.fillOval(xPos - 20, yPos - 20, 40, 40);
                g2d.setColor(Color.WHITE);
                g2d.setFont(new Font("Arial", Font.BOLD, 12));
                FontMetrics metrics = g2d.getFontMetrics();
                String name = pathRoute.get(i).getName();
                g2d.drawString(name, xPos - metrics.stringWidth(name) / 2, yPos + 5);
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new LongHornGui().setVisible(true));
    }
}
/////