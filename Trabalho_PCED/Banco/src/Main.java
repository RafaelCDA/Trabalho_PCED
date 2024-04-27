import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Main {
    public static void main(String[] args) {
        // Criar um objeto Lock
        Lock lock = new ReentrantLock();

        // Criar banco
        Banco banco = new Banco(lock);

        // Criar contas para as lojas
        Conta contaLoja1 = new Conta("Loja1", 0);
        Conta contaLoja2 = new Conta("Loja2", 0);

        // Criar contas para os funcionários da loja 1
        Conta contaSalario1 = new Conta("Salario1", 0);
        Conta contaInvestimento1 = new Conta("Investimento1", 0);
        Conta contaSalario2 = new Conta("Salario2", 0);
        Conta contaInvestimento2 = new Conta("Investimento2", 0);

        // Criar funcionários da loja 1
        FuncionarioImpl funcionario1 = new FuncionarioImpl(contaSalario1, contaInvestimento1);
        FuncionarioImpl funcionario2 = new FuncionarioImpl(contaSalario2, contaInvestimento2);

        // Criar contas para os funcionários da loja 2
        Conta contaSalario3 = new Conta("Salario3", 0);
        Conta contaInvestimento3 = new Conta("Investimento3", 0);
        Conta contaSalario4 = new Conta("Salario4", 0);
        Conta contaInvestimento4 = new Conta("Investimento4", 0);

        // Criar funcionários da loja 2
        FuncionarioImpl funcionario3 = new FuncionarioImpl(contaSalario3, contaInvestimento3);
        FuncionarioImpl funcionario4 = new FuncionarioImpl(contaSalario4, contaInvestimento4);

        // Criar listas de funcionários para as lojas
        List<Funcionario> funcionariosLoja1 = new ArrayList<>();
        List<Funcionario> funcionariosLoja2 = new ArrayList<>();

    
        funcionariosLoja1.add(funcionario1);
        funcionariosLoja1.add(funcionario2);
        funcionariosLoja2.add(funcionario3);
        funcionariosLoja2.add(funcionario4);


        Loja loja1 = new Loja(contaLoja1, funcionariosLoja1, banco);
        Loja loja2 = new Loja(contaLoja2, funcionariosLoja2, banco);

 
        funcionario1.start();
        funcionario2.start();
        funcionario3.start();
        funcionario4.start();

        for (int i = 1; i <= 5; i++) {
            Conta contaCliente = new Conta("Cliente" + i, 1000);
            Cliente cliente = new Cliente(contaCliente, banco, loja1, loja2);
            cliente.start();
        }

        try {
            Thread.sleep(20000); 
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        loja1.pagarFuncionarios();
        loja2.pagarFuncionarios();

        System.out.println("Funcionários pagos nas lojas.");

       
        System.out.println("Dinheiro restante na loja 1: " + loja1.getConta().getSaldo());
        System.out.println("Dinheiro restante na loja 2: " + loja2.getConta().getSaldo());

        // Imprimir saldo dos clientes
        for (int i = 1; i <= 5; i++) {
            Conta contaCliente = new Conta("Cliente" + i, 000);
            System.out.println("Saldo do cliente " + contaCliente.getId() + ": " + contaCliente.getSaldo());
        }

        // Imprimir saldo e investimento de cada funcionário
        System.out.println("Saldo e investimento de cada funcionário:");
        for (Funcionario funcionario : funcionariosLoja1) {
            System.out.println("Funcionário na loja 1 - Saldo: " + funcionario.getContaSalario().getSaldo()
                    + " - Investimento: " + funcionario.getContaInvestimento().getSaldo());
        }
        for (Funcionario funcionario : funcionariosLoja2) {
            System.out.println("Funcionário na loja 2 - Saldo: " + funcionario.getContaSalario().getSaldo()
                    + " - Investimento: " + funcionario.getContaInvestimento().getSaldo());
        }
    }
}
