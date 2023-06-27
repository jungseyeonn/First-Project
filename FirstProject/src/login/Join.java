package login;

import java.awt.Button;
import java.awt.Choice;
import java.awt.Label;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class Join extends WindowAdapter implements ActionListener{
	private JFrame fJoin;
	private Choice jco;
	private TextField tfid, tfpwd, tfrep, tfemail;
	private Button bj;
	private LoginDAO dao;
	
	public Join() {		
		fJoin = new JFrame("회원가입");
		fJoin.setSize(400,500);
		fJoin.setLocation(750, 300);
		fJoin.setLayout(null);
		fJoin.addWindowListener(this);
		
		jco = new Choice();
		jco.add("선생님");
		jco.add("개인");
		jco.setBounds(40, 30, 200, 40);
		fJoin.add(jco);
		
		Label lid = new Label("아이디 : ");
		lid.setBounds(40, 80, 70, 40);
        fJoin.add(lid);
        
        tfid = new TextField();
        tfid.setBounds(140, 80, 200, 30);
        fJoin.add(tfid);
        
        Label lpwd = new Label("비밀번호 : ");
        lpwd.setBounds(40, 140, 70, 40);
        fJoin.add(lpwd);
        
        tfpwd = new TextField();
        tfpwd.setBounds(140, 150, 200, 30);
        tfpwd.setEchoChar('*');
        fJoin.add(tfpwd);
        
        Label lrepwd = new Label("비밀번호 재확인 : ");
        lrepwd.setBounds(13, 220, 100, 40);
        fJoin.add(lrepwd);
        
        tfrep = new TextField();
        tfrep.setBounds(140, 220, 200, 30);
        tfrep.setEchoChar('*');
        fJoin.add(tfrep);
        
        Label lemail = new Label("이메일 : ");
        lemail.setBounds(40, 290, 70, 40);
        fJoin.add(lemail);
        
        tfemail = new TextField();
        tfemail.setBounds(140, 290, 200, 30);
        fJoin.add(tfemail);
        
        bj = new Button("회원가입"); 
        bj.setBounds(130, 350, 80, 40);
        bj.addActionListener(this);
        fJoin.add(bj);
        
        bj.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		signup();
        	}
        });
        
        fJoin.setVisible(true);
	}
	
	private void signup() {
		String id = tfid.getText();
		System.out.println(id);
		String pwd = tfpwd.getText();
		System.out.println(pwd);
		String email = tfemail.getText();
		System.out.println(email);
		String div = jco.getSelectedItem();
		System.out.println(div);
		
		dao.insert(id, pwd,email,div);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		dao = new LoginDAO();
		if(tfid.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "아이디를 입력해주세요.", "ERROR",JOptionPane.ERROR_MESSAGE);
		} else if(tfpwd.getText().isEmpty() || tfrep.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "비밀번호를 입력해주세요.", "ERROR",JOptionPane.ERROR_MESSAGE);
		} else if(!tfrep.getText().equals(tfpwd.getText())) {
			JOptionPane.showMessageDialog(null, "비밀번호가 일치하지 않습니다.", "ERROR",JOptionPane.ERROR_MESSAGE);
		} else if(tfemail.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "이메일을 입력해주세요.", "ERROR",JOptionPane.ERROR_MESSAGE);
		} else {
			JOptionPane.showMessageDialog(null, "회원가입이 완료되었습니다. 로그인 해주세요.", "PLAIN_MESSAGE", JOptionPane.PLAIN_MESSAGE);
			fJoin.dispose();
		}
	}
}
