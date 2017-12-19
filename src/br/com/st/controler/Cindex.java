package br.com.st.controler;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.remoting.rmi.RmiProxyFactoryBean;
import org.zkoss.zhtml.Link;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.ListModelArray;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Textbox;

import br.com.diego.financas.modelo.Conta;
import br.com.diego.financas.rmi.ContaRMIService;

@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class Cindex extends SelectorComposer<Component> {

	
	private static String CONTARMISERVICE_URL = "rmi://localhost:1099/ContaRMIService";
	private static String MOVIMENTACAO_RMI_SERVICE_URL = "rmi://localhost:1099/ContaRMIService";
	/**
	 * Adicionar no controler o codigo abaixo
	 */
	private static final long serialVersionUID = 1L;

	// Aqui ele ira vincular o botão com a textbox //
	@Wire
	private Textbox txtbxMaiuscula;

	@Wire
	private Listbox lstbxOperacoesRealizadas;

	private ArrayList<LinkedHashMap<String, String>> listaOperacoes = new ArrayList<LinkedHashMap<String, String>>();

	
	
	
	// Metodo que ira ser acionado ao clicar no btnMaiuscula //
	@Listen("onClick = #btnMaiuscula")
	public void trocarPorMaiuscula() {
		String antes = this.txtbxMaiuscula.getValue(); // Aqui ele pega o valor
														// inserido
		String depois = antes.toUpperCase(); // Aqui ele modifica o valor
		this.txtbxMaiuscula.setValue(depois); // Aqui ele seta o valor
												// modificado

		LinkedHashMap<String, String> hash = new LinkedHashMap<String, String>();
		hash.put("operacoes", "Maiúscula");
		hash.put("antes", antes); // Aqui ele mostra o valor anterior
		hash.put("depois", depois); // O valor modificado
		listaOperacoes.add(hash); // Adiciona a lista

		
		try {
			ContaRMIService contaRMIService = (ContaRMIService) Naming.lookup(CONTARMISERVICE_URL);
			List<Conta> contas = contaRMIService.getAllContas();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NotBoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		this.lstbxOperacoesRealizadas.setModel(new ListModelArray<LinkedHashMap<String, String>>(this.listaOperacoes));
	}

	// Aqui ele ira vincular o botão com a textbox //
	@Wire
	private Textbox txtbxMinusculo;

	@Listen("onClick = #btnMinusculo")
	public void trocarPorMinuscula() {
		String antes = this.txtbxMinusculo.getValue();
		String depois = antes.toLowerCase();
		this.txtbxMinusculo.setValue(depois);

		LinkedHashMap<String, String> hash = new LinkedHashMap<String, String>();
		hash.put("operacoes", "Minúscula");
		hash.put("antes", antes);
		hash.put("depois", depois);
		listaOperacoes.add(hash);

		this.lstbxOperacoesRealizadas.setModel(new ListModelArray<LinkedHashMap<String, String>>(this.listaOperacoes));
	}

}