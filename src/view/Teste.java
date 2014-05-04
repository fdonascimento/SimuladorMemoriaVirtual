package view;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JTextField;
import javax.swing.JComboBox;

import exceptions.MemoriaVirtualCheiaException;

import simulador.Endereco;
import simulador.Gerenciador;
import simulador.Pagina;
import simulador.Processo;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.Observable;
import java.util.Observer;
import java.util.ArrayList;

public class Teste extends JFrame implements Observer {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	private Gerenciador gerenciador;
	
	private JPanel contentPane;
	private JTextField txtId;
	private JTextField txtTamanho;
	private JTextField txtCusto;	
	private JLabel lblId;
	private JLabel lblTamanho;
	private JLabel lblCusto;
	private JComboBox<Character> cmbProcessos;
	private JLabel lblProcessos;
	private JList<Endereco> lstMemoriaVirtual;
	private JList<Endereco> lstFila;	
	private JList<Pagina> lstDisco;
	private JList<Entry<Endereco, Boolean>> lstTabelaPaginas;	
	private JList<Pagina> lstMemoriaRam;
	private JLabel lblMemoriaVirtual;
	private JLabel lblDisco;	
	private JLabel lblTabelaPaginas;
	private JLabel lblMemoriaRam;	
	private JLabel lblFila;
	private JButton btnRemover;
	private JButton btnAdicionar;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Teste frame = new Teste();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Teste() {
		gerenciador = Gerenciador.getInstance();
		gerenciador.addObserver(this);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 613, 446);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		initComponents();
		updateComponents();
	}

	private void initComponents(){
		btnAdicionar = new JButton("Adicionar");
		btnAdicionar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				char idProcesso = txtId.getText().charAt(0);
				int idCusto = Integer.parseInt(txtCusto.getText());
				int idTamanho = Integer.parseInt(txtTamanho.getText());
				Processo processo = new Processo(idProcesso, idCusto, idTamanho);
				try {
					gerenciador.adicionarProcesso(processo);
				} catch (MemoriaVirtualCheiaException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				updateComponents();
			}
		});
		btnAdicionar.setBounds(316, 7, 89, 23);
		contentPane.add(btnAdicionar);
		
		lblId = new JLabel("Id:");
		lblId.setBounds(10, 11, 46, 14);
		contentPane.add(lblId);
		
		lblTamanho = new JLabel("Tamanho:");
		lblTamanho.setBounds(91, 11, 63, 14);
		contentPane.add(lblTamanho);
		
		lblCusto = new JLabel("Custo:");
		lblCusto.setBounds(206, 11, 46, 14);
		contentPane.add(lblCusto);
		
		txtId = new JTextField();
		txtId.setBounds(35, 8, 46, 20);
		contentPane.add(txtId);
		txtId.setColumns(10);
		
		txtTamanho = new JTextField();
		txtTamanho.setBounds(150, 8, 46, 20);
		contentPane.add(txtTamanho);
		txtTamanho.setColumns(10);
		
		txtCusto = new JTextField();
		txtCusto.setBounds(250, 8, 46, 20);
		contentPane.add(txtCusto);
		txtCusto.setColumns(10);
		
		cmbProcessos = new JComboBox<Character>();
		cmbProcessos.setBounds(91, 47, 89, 20);
		contentPane.add(cmbProcessos);
		
		lblProcessos = new JLabel("Processos:");
		lblProcessos.setBounds(13, 50, 73, 14);
		contentPane.add(lblProcessos);
		
		lstMemoriaVirtual = new JList<Endereco>();
		lstMemoriaVirtual.setBounds(23, 103, 58, 237);
		contentPane.add(lstMemoriaVirtual);
		
		lstFila = new JList<Endereco>();
		lstFila.setLayoutOrientation(JList.HORIZONTAL_WRAP);
		lstFila.setBounds(527, 103, 46, 237);
		contentPane.add(lstFila);
		
		lstDisco = new JList<Pagina>();
		lstDisco.setBounds(103, 103, 159, 237);
		contentPane.add(lstDisco);
		
		lstTabelaPaginas = new JList<Entry<Endereco, Boolean>>();
		lstTabelaPaginas.setBounds(304, 103, 110, 237);
		contentPane.add(lstTabelaPaginas);
		
		lstMemoriaRam = new JList<Pagina>();
		lstMemoriaRam.setBounds(440, 103, 63, 237);
		contentPane.add(lstMemoriaRam);
		
		lblMemoriaVirtual = new JLabel("Mem\u00F3ria Virtual");
		lblMemoriaVirtual.setBounds(23, 74, 89, 23);
		contentPane.add(lblMemoriaVirtual);
		
		lblDisco = new JLabel("Disco");
		lblDisco.setBounds(169, 78, 46, 14);
		contentPane.add(lblDisco);
		
		lblTabelaPaginas = new JLabel("Tabela de P\u00E1ginas");
		lblTabelaPaginas.setBounds(304, 78, 95, 14);
		contentPane.add(lblTabelaPaginas);
		
		lblMemoriaRam = new JLabel("Mem\u00F3ria RAM");
		lblMemoriaRam.setBounds(430, 78, 79, 14);
		contentPane.add(lblMemoriaRam);
		
		lblFila = new JLabel("Fila");
		lblFila.setBounds(527, 78, 46, 14);
		contentPane.add(lblFila);
		
		btnRemover = new JButton("Remover");
		btnRemover.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				Character idProcesso = (Character)cmbProcessos.getSelectedItem();
				gerenciador.removerProcesso(idProcesso);
				updateComponents();
			}
		});
		btnRemover.setBounds(190, 46, 89, 23);
		contentPane.add(btnRemover);
	}
	
	@Override
	public void update(Observable o, Object arg) {
		
	}
	
	private void updateComponents(){
		updateMemoriaVirtual();
		updateDisco();
		updateCombo();
		updateMemoriaRam();
		updateEstruturaDados();
		updateTabelaPagina();
	}
	
	private void updateTabelaPagina(){
		Iterator<Entry<Endereco, Boolean>> iterator = gerenciador.getTabelaPagina().createIterator();
		DefaultListModel<Entry<Endereco, Boolean>> model = new DefaultListModel<Entry<Endereco, Boolean>>();
		
		while(iterator.hasNext()){
			model.addElement(iterator.next());
		}
		
		lstTabelaPaginas.setModel(model);
	}
	
	private void updateMemoriaRam(){
		Iterator<Pagina> iteratorPagina = gerenciador.getMemoriaRam().createIterator();
		DefaultListModel<Pagina> modelPaginas = new DefaultListModel<Pagina>();
		while(iteratorPagina.hasNext()){
			modelPaginas.addElement(iteratorPagina.next());
		}
		
		lstMemoriaRam.setModel(modelPaginas);
	}

	private void updateEstruturaDados(){
		Iterator<Endereco> iteratorEndereco = gerenciador.getEstruturaDados().createIterator();
		DefaultListModel<Endereco> modelEnderecos = new DefaultListModel<Endereco>();
		while(iteratorEndereco.hasNext()){
			modelEnderecos.addElement(iteratorEndereco.next());
		}
		
		lstFila.setModel(modelEnderecos);
	}
	
	private void updateMemoriaVirtual(){
		Iterator<Endereco> iteratorEndereco = gerenciador.getMemoriaVirtual().createIterator();
		DefaultListModel<Endereco> modelEnderecos = new DefaultListModel<Endereco>();
		
		while(iteratorEndereco.hasNext()){
			modelEnderecos.addElement(iteratorEndereco.next());
		}
		lstMemoriaVirtual.setModel(modelEnderecos);
	}
	
	private void updateDisco(){
		Iterator<Pagina> iteratorPagina = gerenciador.getDisco().createIterator();
		DefaultListModel<Pagina> modelPaginas = new DefaultListModel<Pagina>();
		
		while(iteratorPagina.hasNext()){
			modelPaginas.addElement(iteratorPagina.next());
		}
		
		lstDisco.setModel(modelPaginas);
	}
	
	
	private void updateCombo(){
		Iterator<Endereco> iteratorEndereco = gerenciador.getMemoriaVirtual().createIterator();
		List<Character> idProcessos = new ArrayList<Character>();
		
		while(iteratorEndereco.hasNext()){
			Endereco endereco = iteratorEndereco.next();
			Character id = endereco.getIdProcesso();
			boolean alreadyAdded = false;
			for(Character idP : idProcessos){
				if(idP == id){
					alreadyAdded = true;
				}
			}
			if(!alreadyAdded){
				idProcessos.add(id);
			}
		}
		
		DefaultComboBoxModel<Character> idModel = new DefaultComboBoxModel<Character>();
		for(Character c : idProcessos){
			idModel.addElement(c);
		}
		cmbProcessos.setModel(idModel);
	}
}
