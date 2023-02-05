package main;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import modelo.Emp;
import util.SessionFactoryUtil;

public class ConsultasAsociacionesHQL {

	public static void main(String[] args) {
		SessionFactory sessionFactory = SessionFactoryUtil.getSessionFactory();

		Session session = sessionFactory.openSession();

		{
			//1.Los nombres de los departamentos que no tengan empleados. 
			// Los departamentos deben ser ordenados por nombre
			System.out.println("Apartado 1");
			
			List<String> departamentos = session.createQuery(
					" SELECT d.dname FROM Departamento d WHERE size (d.emps) = 0 ORDER BY d.dname ")
					.list();

			for (String departamento : departamentos) {
				System.out.println("Departamento: " + departamento);
			}
		}
		
		{
			 /* 2.Los nombres de los departamentos y de los empleados 
			que tienen al menos 2 empleados. 
			El resultado debe ser ordenado por nombre de departamento */
			
			System.out.println("Apartado 2");
			
			List<Object[]> datos = session.createQuery(
					" SELECT d.dname, e.ename FROM Departamento d LEFT JOIN d.emps e"
					+ " WHERE size (d.emps) >= 2 ORDER BY d.dname")
					.list(); 

			for (Object[] fila : datos) {
				System.out.println("Departamento: " + fila[0] + " - Empleado: " + fila[1]);
			}
		}
		
		{
			// 3.Los identificadores de los empleados y el nº de cuentas por empleado
			
			System.out.println("Apartado 3");
			
			List<Object[]> datos = session.createQuery(
					" SELECT e.empno, size (e.accounts) FROM Emp e")
					.list(); 

			for (Object[] fila : datos) {
				System.out.println("Id empleado: " + fila[0] + " - Nº Cuentas: " + fila[1]);
			}
			
		}
		
		{
			// 4.Los identificadores de los empleados y el saldo de sus cuentas
			
			System.out.println("Apartado 4");
			
			List<Object[]> datos = session.createQuery(
					" SELECT e.empno, c.amount FROM Emp e LEFT JOIN e.accounts c")
					.list(); 

			for (Object[] fila : datos) {
				System.out.println("Id empleado: " + fila[0] + " - Cantidad: " + fila[1]);
			}
			
		}
		
		{
			/* 5.El identificador de cada cuenta con el identificador del movimiento 
			donde la cuenta es la cuenta origen */
			
			System.out.println("Apartado 5");
			
			List<Object[]> datos = session.createQuery(
					" SELECT c.accountOrigen.accountno, c.accountMovId From AccMovement c")
					.list(); 
			System.out.println(datos.size());

			for (Object[] fila : datos) {
				System.out.println("Id cuenta: " + fila[0] + " - Id Movimiento: " + fila[1]);
			}
			
			
		}
		
		{
			// 6.El nº de movimientos por cada cuenta origen
			
			System.out.println("Apartado 6");
			
			List<Object[]> datos = session.createQuery(
					" SELECT c.accountno, size (c.accMovementsOrigen) FROM Account c ")
					//" SELECT c.accountno, size (c.accMovements) FROM Account c ")
					.list(); 

			for (Object[] fila : datos) {
				System.out.println("Cuenta: " + fila[0] + " - Movimientos: " + fila[1]);
			}
			
		}
		
		{
			/* 7. El nombre de cada empleado con el de su jefe. 
			 Ha de aparecer el nombre del empleado aunque no tenga jefe
			 */

			System.out.println("Apartado 7");
			
			List<Object[]> datos = session.createQuery(
					" SELECT e.ename, e.emp.ename FROM Emp e ")
					.list(); 
			System.out.println(datos.size());
			
			for (Object[] fila : datos) {
				System.out.println("Empleado: " + fila[0] + " - Jefe: " + fila[1]);
			} 

			/* for (Object[] fila : datos) {
				Emp jefe = (Emp)fila[1];
				System.out.println("Empleado: " + fila[0] + " - Jefe: " + jefe.getEname());
			} */
			
		}
		
				
		session.close();
		sessionFactory.close();
	}
}
