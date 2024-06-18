/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package gui;

import dao.AlunoDAO;
import dao.HistoricoPesoDAO;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import modelo.Aluno;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.RowSorter;
import javax.swing.SortOrder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;


/**
 *
 * @author gusta
 */
public class AlunoGUI extends javax.swing.JFrame {
    

    private DefaultTableModel model;
    
    private java.sql.Date data;
    
    public void setdata(java.sql.Date data){
    this.data = data;
    
    }
    
    public java.sql.Date getdata(){
        return data;
    }
    


    /**
     * Creates new form AlunoGUI
     */
    public AlunoGUI() {
        initComponents();
        jButton7.setVisible(false);
        model = (DefaultTableModel)jTable1.getModel();
        readFromMySQL();
     
        

        
        jTable1.getTableHeader().setReorderingAllowed(false);
        
        jTable1.setDefaultEditor(Object.class,null);
        
        jTable1.setAutoCreateRowSorter(true);
        
        jTable1.getTableHeader().addMouseListener(new MouseAdapter() {
    @Override
    public void mouseClicked(MouseEvent e) {
        int colIndex = jTable1.columnAtPoint(e.getPoint());
        if (colIndex==2) { 
            TableRowSorter<TableModel> sorter = (TableRowSorter<TableModel>) jTable1.getRowSorter();
            List<RowSorter.SortKey> sortKeys = new ArrayList<>(sorter.getSortKeys());
            RowSorter.SortKey key = new RowSorter.SortKey(colIndex, SortOrder.ASCENDING);
            if (sortKeys.contains(key)) {
                key = new RowSorter.SortKey(colIndex, SortOrder.DESCENDING);
            }
            sorter.setSortKeys(Collections.singletonList(key));
            sorter.setComparator(colIndex,Comparator.comparing(o->{
                String date=(String)o;
                if(date.matches("\\d{2}/\\d{2}/\\d{4}")){
                    String[] parts = date.split("/");
                    return Integer.parseInt(parts[2]);
                }else{
                    return 0;
                }              
            }));
        }
    }
});
        
        jTable1.getSelectionModel().addListSelectionListener(new ListSelectionListener(){
            @Override
            public void valueChanged(ListSelectionEvent e){
                if(!e.getValueIsAdjusting()){
                    int selectedRow=jTable1.getSelectedRow();
                    
                    if(selectedRow != -1){
                        String cpf = (String) jTable1.getValueAt(selectedRow,0);
                        String nome = (String) jTable1.getValueAt(selectedRow,1);
                        String dataNascimento = (String) jTable1.getValueAt(selectedRow,2);
                        double peso = (double) jTable1.getValueAt(selectedRow,3);
                        double altura = (double) jTable1.getValueAt(selectedRow,4);
                        
                        
                        jTextField1.setText(cpf);
                        jTextField3.setText(nome);
                        jTextField5.setText(dataNascimento);
                        jTextField2.setText(Double.toString(peso));
                        jTextField4.setText(Double.toString(altura));




                    }
                }
            }
        });
        
    }
    
    private void clearFields(){
        jTextField1.setText("");
        jTextField2.setText("");
        jTextField3.setText("");
        jTextField4.setText("");
        jTextField5.setText("");
    }
    



    

    
    private String formatDecimalString(String input){
        return input.replace(',','.');
    }
    
    private String formatarCPF(String cpf){
        String cpfApenasNumeros=cpf.replaceAll("[^0-9]","");
        if(cpfApenasNumeros.length()!=11){
            return "CPF Inválido";
        }
        
        return cpfApenasNumeros.substring(0,3)+"."+
               cpfApenasNumeros.substring(3,6)+"."+
               cpfApenasNumeros.substring(6,9)+"-"+
               cpfApenasNumeros.substring(9);
    }
    
    private void readFromMySQL(){
        Connection conn=null;
        Statement stmt=null;
        ResultSet rs=null;
        
        try{
            conn=DriverManager.getConnection("jdbc:mysql://localhost:3306/escola", "root", "Corinthians13");
            
            stmt= conn.createStatement();
            String sql="SELECT * FROM aluno";
            rs=stmt.executeQuery(sql);
            model.setRowCount(0);
            while (rs.next()){
                String Cpf = rs.getString("cpf");
                String Nome = rs.getString("nome");
                String Data_nascimento = rs.getString("data_nascimento");
                double Peso = rs.getDouble("peso");
                double Altura = rs.getDouble("altura");
                
                LocalDate dataNascimento = LocalDate.parse(Data_nascimento,DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                String Data_nascimento_normal=dataNascimento.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
                
                model.addRow(new Object[]{Cpf,Nome,Data_nascimento_normal,Peso,Altura});
                
            }
        }catch(SQLException e){
            JOptionPane.showMessageDialog(this,"Erro ao conectar ao banco de dados"+e.getMessage(),"Erro",JOptionPane.ERROR_MESSAGE);
        }finally{
            
           try{
               if(rs!=null)rs.close();
               if(stmt!=null)stmt.close();
               if(conn!=null)conn.close();  
           }catch(SQLException e){
               e.printStackTrace();
           }
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jTextField3 = new javax.swing.JTextField();
        jTextField2 = new javax.swing.JTextField();
        jTextField4 = new javax.swing.JTextField();
        jTextField5 = new javax.swing.JTextField();
        jButton4 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jButton6 = new javax.swing.JButton();
        jButton7 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton8 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Cadastros de Alunos");

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        jLabel2.setText("CPF:");

        jTextField1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField1ActionPerformed(evt);
            }
        });

        jButton1.setText("Cadastrar");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton3.setText("Sair");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jLabel3.setText("Nome:");

        jLabel4.setText("Data de Nascimento:");

        jLabel5.setText("Peso:");

        jLabel6.setText("Altura:");

        jTextField3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField3ActionPerformed(evt);
            }
        });

        jTextField2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField2ActionPerformed(evt);
            }
        });

        jTextField5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField5ActionPerformed(evt);
            }
        });

        jButton4.setText("Atualizar");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jButton5.setText("Deletar");
        jButton5.setToolTipText("");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        jPanel3.setBackground(new java.awt.Color(204, 204, 204));

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Cpf", "Nome", "Data de Nascimento", "Peso", "Altura"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Double.class, java.lang.Double.class
            };
            boolean[] canEdit = new boolean [] {
                true, true, true, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(jTable1);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 668, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(15, 15, 15))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jButton6.setText("Consultar");
        jButton6.setToolTipText("");
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });

        jButton7.setText("Voltar");
        jButton7.setToolTipText("");
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });

        jButton2.setText("Limpar");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton8.setText("Histórico de Peso");
        jButton8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton8ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jTextField5, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jButton1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton4)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jButton5)))
                        .addGap(3, 3, 3)
                        .addComponent(jButton6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton8)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton3))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(317, 317, 317)
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 676, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 16, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(26, 26, 26)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(35, 35, 35)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5)
                            .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(29, 29, 29)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6)
                            .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(32, 32, 32)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(jTextField5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(169, 169, 169))
                    .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 25, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton5)
                    .addComponent(jButton1)
                    .addComponent(jButton4)
                    .addComponent(jButton6)
                    .addComponent(jButton7)
                    .addComponent(jButton3)
                    .addComponent(jButton2)
                    .addComponent(jButton8))
                .addGap(44, 44, 44))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addGap(316, 316, 316)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 340, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(373, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jTextField5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField5ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField5ActionPerformed

    private void jTextField2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
        System.exit(0);
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        String pesoStr= formatDecimalString(jTextField2.getText());
        String alturaStr=formatDecimalString(jTextField4.getText());

        
        if (jTextField1.getText().isEmpty() || jTextField3.getText().isEmpty() || jTextField5.getText().isEmpty() || jTextField2.getText().isEmpty() || jTextField4.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Todos os campos são obrigatórios.");
            return;
        }
        
        AlunoDAO dao= new AlunoDAO();
        
       
        
        String cpf = jTextField1.getText();
        cpf=formatarCPF(cpf);
        if ("CPF Inválido".equals(cpf)){
            JOptionPane.showMessageDialog(null,"O CPF Inválido");
            return;
        
        }
        
        if(dao.existAluno(cpf)){
            JOptionPane.showMessageDialog(null,"O CPF já está cadastrado");
            return;
        }
        
        String dataNascimentoStr=jTextField5.getText();
        SimpleDateFormat sdf=new SimpleDateFormat("dd/MM/yyyy");
        sdf.setLenient(false);
        
        try{
            Date dataNascimento=sdf.parse(dataNascimentoStr);
        
        
        
        Aluno aluno = new Aluno();
        aluno.setCpf(jTextField1.getText());
        aluno.setNome(jTextField3.getText());
        aluno.setData_nascimento(jTextField5.getText());
        aluno.setPeso(Double.parseDouble(pesoStr));
        aluno.setAltura(Double.parseDouble(alturaStr));
        
        aluno.setCpf(cpf);
        
            dao.adiciona(aluno);
            JOptionPane.showMessageDialog(null, "Aluno " + jTextField1.getText() + " inserido com sucesso!");
            model.addRow(new Object[]{aluno.getCpf(),aluno.getNome(),aluno.getData_nascimento(),aluno.getPeso(),aluno.getAltura()});
        
            LocalDate data = LocalDate.now();
            
            HistoricoPesoDAO  historicopesoDao = new HistoricoPesoDAO();
            historicopesoDao.salvarPesoHistorico(aluno.getCpf(),aluno.getPeso(),aluno.getAltura(),data);
            
            
            

        jTextField1.setText("");
        jTextField2.setText("");
        jTextField3.setText("");
        jTextField4.setText("");
        jTextField5.setText("");
        
    }catch(ParseException ex){
        JOptionPane.showMessageDialog(null,"Data de Nascimento Inválida","Erro",JOptionPane.ERROR_MESSAGE);
    }   catch (SQLException ex) {
            Logger.getLogger(AlunoGUI.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        // TODO add your handling code here:
        String pesoStr= formatDecimalString(jTextField2.getText());
        String alturaStr=formatDecimalString(jTextField4.getText());
        int selectedRow = jTable1.getSelectedRow();
        
        if(selectedRow==-1){
            JOptionPane.showMessageDialog(null,"Nenhum aluno selecionado.");
            return;
        }
        String cpf=(String) jTable1.getValueAt(selectedRow, 0);
        String cpfFormatado=formatarCPF(cpf);
        String novoCpf=jTextField1.getText();
        if (!novoCpf.equals(cpf)){
            cpf=novoCpf;
        }
        cpf=formatarCPF(cpf);
        String Nome=jTextField3.getText();
        String Data_nascimento= jTextField5.getText();
        double peso;
        double altura;
        try{
            peso=Double.parseDouble(pesoStr);
            altura=Double.parseDouble(alturaStr);
        }catch (NumberFormatException ex){
            return;
        }
        
        SimpleDateFormat sdf=new SimpleDateFormat("dd/MM/yyyy");
        sdf.setLenient(false);
        
        try{
            Date dataNascimento=sdf.parse(Data_nascimento);
        
   
        Aluno aluno=new Aluno();
        aluno.setCpf(cpf);
        aluno.setNome(Nome);
        aluno.setData_nascimento(Data_nascimento);
        aluno.setPeso(peso);
        aluno.setAltura(altura);
        
        AlunoDAO dao=new AlunoDAO();
        dao.update(aluno);
        
        LocalDate dataAtualizacao=LocalDate.now();
        HistoricoPesoDAO historicopesoDao= new HistoricoPesoDAO();
        historicopesoDao.salvarPesoHistorico(aluno.getCpf(), aluno.getPeso(), aluno.getAltura(), dataAtualizacao);
        
        model.setValueAt(aluno.getCpf(), selectedRow,0);
        model.setValueAt(aluno.getNome(),selectedRow,1);
        model.setValueAt(aluno.getData_nascimento(),selectedRow,2);
        model.setValueAt(aluno.getPeso(),selectedRow,3);
        model.setValueAt(aluno.getAltura(),selectedRow,4);
        
        JOptionPane.showMessageDialog(null,"Aluno atualizado com sucesso!");
      }catch(ParseException ex){
        JOptionPane.showMessageDialog(null, "Data de Nascimento Inválida","Erro",JOptionPane.ERROR_MESSAGE);
        } catch (SQLException ex) {
            Logger.getLogger(AlunoGUI.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        // TODO add your handling code here:
        int selectedRow = jTable1.getSelectedRow();
        
        if(selectedRow==-1){
            JOptionPane.showMessageDialog(null, "Nenhum aluno selecionado.");
            return;
                  } 
        String Cpf =(String) jTable1.getValueAt(selectedRow,0);
        
        int option = JOptionPane.showConfirmDialog(null,"Tem certeza que deseja excluir este aluno?");
        if(option==JOptionPane.YES_OPTION){
            
            AlunoDAO dao=new AlunoDAO();
            dao.delete(Cpf);
            
            DefaultTableModel model=(DefaultTableModel) jTable1.getModel();
            model.removeRow(selectedRow);
            clearFields();
            JOptionPane.showMessageDialog(null, "Aluno excluído com sucesso!");
        }
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
       String cpf = JOptionPane.showInputDialog("Digite o CPF do aluno:");

        if (cpf != null && !cpf.isEmpty()) {
            cpf = formatarCPF(cpf);
            if("CPF Inválido".equals(cpf)){
                JOptionPane.showMessageDialog(null,"CPF Inválido");
                return;
            }
            AlunoDAO dao = new AlunoDAO();
            Aluno aluno = dao.consultar(cpf);
            
            
            if (aluno != null) {
                model.setRowCount(0);
                LocalDate dataNascimento = LocalDate.parse(aluno.getData_nascimento(), DateTimeFormatter.ofPattern("yyyy-MM-dd")); 
                String dataNascimentoFormatada = dataNascimento.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
                model.addRow(new Object[]{aluno.getCpf(), aluno.getNome(), dataNascimentoFormatada, aluno.getPeso(), aluno.getAltura()});
                jButton7.setVisible(true);
            } else {
                JOptionPane.showMessageDialog(null, "Aluno não encontrado.");
            }
        }
        
        
        
    }//GEN-LAST:event_jButton6ActionPerformed

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
    Connection conn=null;
        Statement stmt=null;
        ResultSet rs=null;
        
        try{
            conn=DriverManager.getConnection("jdbc:mysql://localhost:3306/escola", "root", "Corinthians13");
            
            stmt= conn.createStatement();
            String sql="SELECT * FROM aluno";
            rs=stmt.executeQuery(sql);
            model.setRowCount(0);
            while (rs.next()){
                String Cpf = rs.getString("cpf");
                String Nome = rs.getString("nome");
                String Data_nascimento = rs.getString("data_nascimento");
                double Peso = rs.getDouble("peso");
                double Altura = rs.getDouble("altura");
                
                LocalDate dataNascimento = LocalDate.parse(Data_nascimento, DateTimeFormatter.ofPattern("yyyy-MM-dd")); 
                String dataNascimentoFormatada = dataNascimento.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
                
                
                model.addRow(new Object[]{Cpf,Nome,dataNascimentoFormatada,Peso,Altura});
                
            }
        }catch(SQLException e){
            JOptionPane.showMessageDialog(this,"Erro ao conectar ao banco de dados"+e.getMessage(),"Erro",JOptionPane.ERROR_MESSAGE);
        }finally{
            
           try{
               if(rs!=null)rs.close();
               if(stmt!=null)stmt.close();
               if(conn!=null)conn.close();  
           }catch(SQLException e){
               e.printStackTrace();
           }
        }
        jButton7.setVisible(false);
        
    }//GEN-LAST:event_jButton7ActionPerformed

    private void jTextField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField1ActionPerformed

    private void jTextField3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField3ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        jTextField1.setText("");
        jTextField2.setText("");
        jTextField3.setText("");
        jTextField4.setText("");
        jTextField5.setText("");
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton8ActionPerformed
        // TODO add your handling code here:
      HistoricoPesoGUI historicoFrame = new HistoricoPesoGUI();
    historicoFrame.setVisible(true);

    }//GEN-LAST:event_jButton8ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(AlunoGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(AlunoGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(AlunoGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(AlunoGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new AlunoGUI().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton8;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JTextField jTextField4;
    private javax.swing.JTextField jTextField5;
    // End of variables declaration//GEN-END:variables
}
