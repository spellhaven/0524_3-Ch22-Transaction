package com.spellhaven.spring0524_3;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

public class TicketDao {
	
	JdbcTemplate template;
	TransactionTemplate transactionTemplate; // 얘는 Bean이랑 이름이 같아야 한다. 조심하자 ㅋ
	

	// 어. 기억나지? 애기야, template는 세터만 있으면 돼, ㅋ, 😎
	public void setTemplate(JdbcTemplate template) {
		this.template = template;
	}
	
	
	// 얘 이름 Whyrano 😩 여튼 얘도 세터 없더니 에러 떴다. 세터 없이는 Dependency를 Inject할 수가 업잔아.
	public void setTransactionTemplate(TransactionTemplate transactionTemplate) {
		this.transactionTemplate = transactionTemplate;
	}


	public TicketDao() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	// 아까 배운 JDBC 템플릿을 이용해 보자. INSERT INTO문이니까 template.update()다.
	// 고객이 구매하려는 정보가 DB의 card 테이블과 ticket 테이블에 둘 다 들어가는 거지. 그래서 이 안에 2개 있는 거야.
	public void buyTicket(final TicketDto dto) {
		
		
		// 어. transactionTemplate.execute 얘가 트랜잭션 시켜 주는 놈이야. (Spring에 들어와서는 이름이 한바가지인 놈들만 만난다;; 다들 이름이 무슨
		// 파블로 딩고 호세 프란시스코 드파우라 호안 네포무세노 마리아 드로스 레메디오스 크리스피노 드라산티시스마 트리니다드 루이즈 이 피카소 뭐 이런 식임;;)
		transactionTemplate.execute(new TransactionCallbackWithoutResult() {
			
			@Override
			protected void doInTransactionWithoutResult(TransactionStatus status) {
				
				template.update(new PreparedStatementCreator() {
					
					@Override
					public PreparedStatement createPreparedStatement(Connection con) throws SQLException {

						String query = "INSERT INTO card(consumerid, amount) VALUES(?, ?)";
						PreparedStatement pstmt = con.prepareStatement(query);
						pstmt.setString(1, dto.getConsumerid());		
						pstmt.setString(2, dto.getAmount());		
						
						return pstmt;
					}
				});
				
				
				template.update(new PreparedStatementCreator() {
					
					@Override
					public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
						
						String query = "INSERT INTO ticket(consumerid, countnum) VALUES(?, ?)";
						PreparedStatement pstmt = con.prepareStatement(query);
						pstmt.setString(1, dto.getConsumerid());		
						pstmt.setString(2, dto.getAmount());		
						
						return pstmt;
					}
				});
			}
		});
		
		
		
	}
}





























