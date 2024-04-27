import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Banco {
    private Lock lock;

    public Banco() {
        this.lock = new ReentrantLock();
    }

    public Banco(Lock lock) {
        this.lock = lock;
    }

    public void transferencia(Conta origem, Conta destino, double valor) {
        lock.lock();
        try {
            if (origem.getSaldo() >= valor) {
                origem.sacar(valor);
                destino.depositar(valor);
                System.out.println(
                        "Transferência de " + valor + " realizada de " + origem.getId() + " para " + destino.getId());
            } else {
                System.out.println(
                        "Saldo insuficiente para transferência de " + origem.getId() + " para " + destino.getId());
            }
        } finally {
            lock.unlock();
        }

        try {
            Thread.sleep(2000); 
        } catch (InterruptedException e) {
            
            e.printStackTrace();
        }
    }

    public void efetuarPagamento(Funcionario funcionario, double valorSalario, double valorInvestimento) {
        lock.lock();
        try {
            funcionario.receberSalario(valorSalario);
            funcionario.investir(valorInvestimento);
        } finally {
            lock.unlock();
        }
    }
}
