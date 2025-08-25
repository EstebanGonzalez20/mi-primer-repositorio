package src; 

public class IP {
    // Atributos privados
    private String ipCompleta;
    private int numero1;
    private int numero2;
    private int numero3;
    private int numero4;

    // Constructor
    public IP(String ipCompleta) {
        this.ipCompleta = ipCompleta;
        setNumeros();
    }

    // Setters
    public void setIpCompleta(String ipCompleta) {
        this.ipCompleta = ipCompleta;
        setNumeros();
    }

    public void setNumero1(int numero1) {
        this.numero1 = numero1;
        actualizarIpCompleta();
    }

    public void setNumero2(int numero2) {
        this.numero2 = numero2;
        actualizarIpCompleta();
    }

    public void setNumero3(int numero3) {
        this.numero3 = numero3;
        actualizarIpCompleta();
    }

    public void setNumero4(int numero4) {
        this.numero4 = numero4;
        actualizarIpCompleta();
    }

    public void actualizarIpCompleta(){
        ipCompleta = numero1 + "." + numero2 + "." + numero3 + "." + numero4;
    }

    public void setNumeros(){
        numero1 = Integer.parseInt(ipCompleta.substring(0, ipCompleta.indexOf('.')));
        numero2 = Integer.parseInt(ipCompleta.substring(ipCompleta.indexOf('.') + 1, ipCompleta.indexOf('.', ipCompleta.indexOf('.') + 1)));
        numero3 = Integer.parseInt(ipCompleta.substring(ipCompleta.indexOf('.', ipCompleta.indexOf('.') + 1) + 1, ipCompleta.lastIndexOf('.')));
        numero4 = Integer.parseInt(ipCompleta.substring(ipCompleta.lastIndexOf('.') + 1));
    }

    public String getIpCompleta() {
        return ipCompleta;
    }

    public int getNumero4() {
        return numero4;
    }

    public void corregirIP() {
        if (numero4 > 255) {
            numero3 += numero4 / 256;
            numero4 = numero4 % 256;
        }
        if (numero3 > 255) {
            numero2 += numero3 / 256;
            numero3 = numero3 % 256;
        }
        if (numero2 > 255) {
            numero1 += numero2 / 256;
            numero2 = numero2 % 256;
        }
        if (numero1 > 255) {
            numero1 = 255;
            numero2 = 255;
            numero3 = 255;
            numero4 = 255;
        }
        actualizarIpCompleta();
    }

    // Método para validar si una IP es válida

    static public boolean esIPValida(String ip) {
        return ip.matches("^((25[0-5]|2[0-4]\\d|[0-1]?\\d{1,2})\\.){3}(25[0-5]|2[0-4]\\d|[0-1]?\\d{1,2})$");
    }

    /**
     * Calcula la diferencia absoluta entre dos IPs (en formato "a.b.c.d")
     * Devuelve la suma de las diferencias absolutas de cada octeto.
     */
    public static int diferenciaEntreIPs(String ip1, String ip2) {
        String[] partes1 = ip1.split("\\.");
        String[] partes2 = ip2.split("\\.");
        if (partes1.length != 4 || partes2.length != 4) {
            throw new IllegalArgumentException("Las IPs deben tener formato a.b.c.d");
        }
        int diferencia = 0;
        for (int i = 0; i < 4; i++) {
            int n1 = Integer.parseInt(partes1[i]);
            int n2 = Integer.parseInt(partes2[i]);
            diferencia += Math.abs(n1 - n2) * Math.pow(256, 3 - i);
        }
        return diferencia;
    }

    /**
     * Verifica si la IP ip1 es mayor que ip2 (compara octeto por octeto)
     */
    public static boolean esMayorQue(String ip1, String ip2) {
        String[] partes1 = ip1.split("\\.");
        String[] partes2 = ip2.split("\\.");
        if (partes1.length != 4 || partes2.length != 4) {
            throw new IllegalArgumentException("Las IPs deben tener formato a.b.c.d");
        }
        for (int i = 0; i < 4; i++) {
            int n1 = Integer.parseInt(partes1[i]);
            int n2 = Integer.parseInt(partes2[i]);
            if (n1 > n2) return true;
            if (n1 < n2) return false;
        }
        return false; // Son iguales
    }
}
