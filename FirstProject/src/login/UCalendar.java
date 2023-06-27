package login;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

class UCalendar extends JFrame implements ActionListener{
	MainDAO dao = new MainDAO();
	
	String[] days = { "일", "월", "화", "수", "목", "금", "토" };
	int year, month, day, todays, memoday = 0;
	
	Font f;
	Color bc, fc;
	Calendar today, cal;
	
	JButton btnBefore, btnAfter;
	JButton[] calBtn = new JButton[49];
	
	JLabel jlSchool, jlScedule;
	
	JPanel panWest, panSouth, panEast, panNorth;
	
	JTextField txtMonth, txtYear, txtTime;
	JTextArea txtschool,txtschedule;
	BorderLayout bLayout = new BorderLayout();
	
	public UCalendar() {
		today = Calendar.getInstance();
		cal = new GregorianCalendar();
		year = today.get(Calendar.YEAR);
		month = today.get(Calendar.MONTH) + 1;// 1월의 값이 0
		
		panNorth = new JPanel();
		panNorth.add(btnBefore = new JButton("Before"));
		panNorth.add(txtYear = new JTextField(year + "년"));
		panNorth.add(txtMonth = new JTextField(month + "월"));
		txtYear.setEnabled(false);
		txtMonth.setEnabled(false);
		
		panNorth.add(btnAfter = new JButton("After"));
		f = new Font("Sherif", Font.BOLD, 24);
		txtYear.setFont(f);
		txtMonth.setFont(f);
		
		add(panNorth, "North");

		// 달력		
		panWest = new JPanel(new GridLayout(7, 7));// 격자나,눈금형태의 배치관리자
		f = new Font("Sherif", Font.BOLD, 12);
		
		gridInit();
		calSet();
		hideInit();
		add(panWest, "West");

		panEast = new JPanel();
		panEast.add(jlSchool = new JLabel("학교명"));
		panEast.add(txtschool = new JTextArea());
		txtschool.setPreferredSize(new Dimension(140, 280));
		add(panEast, "Center");
		txtschool.setEditable(false);
		txtschool.setFocusable(false);
		txtschool.setFont(new Font("맑은고딕",1,15));

		panEast.add(jlScedule = new JLabel("오늘일정"));
		panEast.add(txtschedule = new JTextArea());
		txtschedule.setPreferredSize(new Dimension(140, 280));
		add(panEast, "Center");
		txtschedule.setEditable(false);
		txtschedule.setFocusable(false);
		txtschedule.setFont(new Font("맑은고딕",1,15));
		
		btnBefore.addActionListener(this);
		btnAfter.addActionListener(this);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("시흥시 일정관리 시스템 - 개인");
		setBounds(630,380,800,400);
		setVisible(true);
	}

	public void calSet() {
		cal.set(Calendar.YEAR, year);
		cal.set(Calendar.MONTH, (month - 1));
		cal.set(Calendar.DATE, 1);
		int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);

		/*get 및 set 를 위한 필드치로, 요일을 나타냅니다.
	이 필드의 값은 SUNDAY,MONDAY,TUESDAY,WEDNESDAY,THURSDAY,FRIDAY, 및 SATURDAY
			get()메소드의 의해 요일이 숫자로 반환*/

		int j = 0;
		int hopping = 0;
		calBtn[0].setForeground(new Color(255, 0, 0));// 일요일 "일"
		calBtn[6].setForeground(new Color(0, 0, 255));// 토요일 "토"
		for (int i = cal.getFirstDayOfWeek(); i < dayOfWeek; i++) {
			j++;
		}
		//일요일부터 그달의 첫시작 요일까지 빈칸으로 셋팅하기 위해
		hopping = j;
		for (int kk = 0; kk < hopping; kk++) {
			calBtn[kk + 7].setText("");
		}
		for (int i = cal.getMinimum(Calendar.DAY_OF_MONTH);
				i <= cal.getMaximum(Calendar.DAY_OF_MONTH); i++) {
			cal.set(Calendar.DATE, i);
			if (cal.get(Calendar.MONTH) != month - 1) {
				break;
			}
			todays = i;
			if (memoday == 1) {
				calBtn[i + 6 + hopping].setForeground(new Color(0, 255, 0));
			}
			else {
				calBtn[i + 6 + hopping].setForeground(new Color(0, 0, 0));
				if ((i + hopping - 1) % 7 == 0) {// 일요일
					calBtn[i + 6 + hopping].setForeground(new Color(255, 0, 0));
				}
				if ((i + hopping) % 7 == 0) {// 토요일
					calBtn[i + 6 + hopping].setForeground(new Color(0, 0, 255));
				}
			}

			/*요일을 찍은 다음부터 계산해야 하니 요일을 찍은 버튼의 갯수를 더하고
 			인덱스가 0부터 시작이니 -1을 해준 값으로 연산을 해주고
			버튼의 색깔을 변경해준다.*/
			calBtn[i + 6 + hopping].setText((i) + "");
		} 
	}
	public void actionPerformed(ActionEvent ae) {
		if (ae.getSource() == btnBefore) {
			this.panWest.removeAll();
			calInput(-1);
			gridInit();
			panelInit();
			calSet();
			hideInit();
			this.txtYear.setText(year + "년");
			this.txtMonth.setText(month + "월");
		}
		else if (ae.getSource() == btnAfter) {
			this.panWest.removeAll();
			calInput(1);
			gridInit();
			panelInit();
			calSet();
			hideInit();
			this.txtYear.setText(year + "년");
			this.txtMonth.setText(month + "월");
		} 
		else if (Integer.parseInt(ae.getActionCommand()) >= 1 && Integer.parseInt(ae.getActionCommand()) <= 31) {
			day = Integer.parseInt(ae.getActionCommand());
			// 버튼의 밸류 즉 1,2,3.... 문자를 정수형으로 변환하여 클릭한 날짜를 바꿔준다.
			System.out.println(day);
			calSet();
			
			ArrayList<MainVo> listca = dao.listca(year, month, day);
			
			if (listca.size()!=0) {
				for(int i=0;i<listca.size();i++) {
					MainVo data = (MainVo) listca.get(i);
					String sd = data.getSch();
					String nd = data.getName();
					if(i==0) {
						txtschool.setText((i+1)+" "+ nd+"\n");
						txtschedule.setText((i+1) +" "+ sd+"\n");
					} else {
						txtschool.append((i+1)+" "+nd+"\n");
						txtschedule.append((i+1)+" "+sd+"\n");
					}
				}
			} else {
				txtschool.setText("");
				txtschedule.setText("일정이 없습니다.");
			}
		}
	}
	public void hideInit() {
		for (int i = 0; i < calBtn.length; i++) {
			if ((calBtn[i].getText()).equals(""))
				calBtn[i].setEnabled(false);
			// 일이 찍히지 않은 나머지 버튼을 비활성화 시킨다.
		} 
	}
	
	public void gridInit() {
	// jPanel3에 버튼 붙이기
		for (int i = 0; i < days.length; i++)
			panWest.add(calBtn[i] = new JButton(days[i]));
		for (int i = days.length; i < 49; i++) {
			panWest.add(calBtn[i] = new JButton(""));
			calBtn[i].addActionListener(this);
		}
	}
	
	public void panelInit() {
		GridLayout gridLayout1 = new GridLayout(7, 7);
		panWest.setLayout(gridLayout1);
	}
	
	public void calInput(int gap) {
		month += (gap);
		if (month <= 0) {
			month = 12;
			year = year - 1;
		} else if (month >= 13) {
			month = 1;
			year = year + 1;
		}
	}
}
