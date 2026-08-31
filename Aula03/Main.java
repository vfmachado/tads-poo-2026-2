import java.util.List;

public class Main {
    public static void main(String[] args)  {
        
        CarrinhoDeCompras carrinho  = new CarrinhoDeCompras("WHAT IF ... HAMBURGUERIA ");

        Produto p1 = new Produto("Hamburguer Classico", 29.9f);
        Produto p2 = new Produto("Hamburguer Duplo Cheddar", 32.9f);
        Produto p3 = new Produto("Coca-Cola Lata", 8);
        Produto p4 = new Produto("Capirinha Maracuja", 24);
        Produto p5 = new Produto("Batata Frita 200g", 19.8f);

        System.out.println(List.of(p1, p2, p3, p4, p5));

        carrinho.adicionarItem(p1, 2, "Completos");
        
        System.out.println(carrinho.calcularValorSubtotal());

        
        carrinho.adicionarItem(p2, 1);

        System.out.println(carrinho.calcularValorSubtotal());

        // carrinho.adicionarItem(p5, 1);

        System.out.println(MoedaUtils.formatarDinheiro(carrinho.calcularValorSubtotal()));


        Cupom cupom = new Cupom("VALE10", 0.1f, 100); // se o cupom ja foi utilizado, nao posso usar novamente
        System.out.println(cupom);

        carrinho.aplicarCupom(cupom);
        double total = carrinho.calcularValorTotal();
        System.out.println(cupom);


        carrinho.adicionarItem(p5, 1);

        total = carrinho.calcularValorTotal();
        System.out.println("TOTAL = " + total);
        System.out.println(cupom);
    }
}
