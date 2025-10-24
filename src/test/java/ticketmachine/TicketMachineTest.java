package ticketmachine;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;

class TicketMachineTest {
	private static final int PRICE = 50; // Une constante

	private TicketMachine machine; // l'objet à tester

	@BeforeEach
	public void setUp() {
 		machine = new TicketMachine(PRICE); // On initialise l'objet à tester
	}

	@Test
	void priceIsCorrectlyInitialized() {
		assertEquals(PRICE, machine.getPrice(), "Initialisation incorrecte du prix");
	}

	@Test
	void insertMoneyChangesBalance() {
		machine.insertMoney(10);
		machine.insertMoney(20);
		assertEquals(10 + 20, machine.getBalance(), "La balance n'est pas correctement mise à jour");
	}

	@Test
	void printTicketIfPyementIsComplete() {
		machine.insertMoney(PRICE -10);
		assertFalse(machine.printTicket(), "Le ticket ne devrait pas être imprimé si le paiement est incomplet");
	}

	@Test
	void printTicketIfPaymentIsComplete() {
		machine.insertMoney(PRICE);
		assertTrue(machine.printTicket(), "Le ticket devrait être imprimé si le paiement est complet");
	}

	@Test
	void balanceDecreasesWhenTicketIsPrinted() {
		machine.insertMoney(PRICE + 20);
		machine.printTicket();
		assertEquals(20, machine.getBalance(), "La balance n'est pas correctement mise à jour après impression du ticket");
	}

	@Test
	void totalIncreasesWhenTicketIsPrinted() {
		machine.insertMoney(PRICE + 20);
		machine.printTicket();
		assertEquals(PRICE, machine.getTotal(), "Le total n'est pas correctement mis à jour après impression du ticket");
	}

	@Test
	void totalNotChangesWhenTicketIsNotPrinted() {
		machine.insertMoney(PRICE - 10);
		machine.printTicket();
		assertEquals(0, machine.getTotal(), "Le total ne devrait pas changer si le ticket n'est pas imprimé");
	}

	@Test
	void testRefundWorks() {
		machine.insertMoney(30);
		int refund = machine.refund();
		assertEquals(30, refund, "Le montant remboursé est incorrect");
		assertEquals(0, machine.getBalance(), "La balance devrait être remise à zéro après remboursement");
	}

	@Test
	void testNotInsertNegativeOrZeroMoney() {
		IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
			machine.insertMoney(0);
		});
		assertEquals("Amount must be positive", exception.getMessage());

		exception = assertThrows(IllegalArgumentException.class, () -> {
			machine.insertMoney(-10);
		});
		assertEquals("Amount must be positive", exception.getMessage());
	}

	@Test
	void testNotNegativeOrZeroPrice() {
		IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
			new TicketMachine(0);
		});
		assertEquals("Ticket price must be positive", exception.getMessage());

		exception = assertThrows(IllegalArgumentException.class, () -> {
			new TicketMachine(-10);
		});
		assertEquals("Ticket price must be positive", exception.getMessage());
	}

}
