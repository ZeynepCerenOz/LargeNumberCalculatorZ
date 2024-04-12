public class Main {
    public static void main(String[] args) {

        LargeNumberCalculator lnc=new LargeNumberCalculator();
        //String total=lnc.add("999","1111");
        // String diff=lnc.subtract(generateLargeNumber("12","66",4),"99");
        //System.out.println(diff);

        //String product=lnc.multiply(generateLargeNumber("45","0",100),"56");

        //System.out.println(product);
        String result=lnc.divide(generateLargeNumber("10","00",20),2);
        System.out.println(result);


    }

    public static String generateLargeNumber(String first,String filling, int numberOfFilling){
        StringBuilder sb=new StringBuilder(first);
        for(int i=1;i<=numberOfFilling;i++){
            sb.append(filling);
        }
        return sb.toString();
    }
}