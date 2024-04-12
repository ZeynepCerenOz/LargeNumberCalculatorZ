//Main class for calculations
public class LargeNumberCalculator {
    // Inner class to represent a node in a linked list
    private class ListNode {
        int digit;
        ListNode next;//will be used for  division
        ListNode prev;//will be used for  additions, subtractions, multiplication

        ListNode(int digit) {
            this.digit = digit;
            this.next = null;
            this.prev = null;
        }
    }

    //Inner class to represent a linked list
    private class LinkedList {
        ListNode head;//will be used for  division
        ListNode tail;//will be used for  additions, subtractions, multiplication
        LinkedList(ListNode head, ListNode tail) {
            this.head = head;
            this.tail = tail;
        }
    }





    public static boolean isNumeric(String strNum) {
        if (strNum == null) {
            return false;
        }
        try {
            double d = Double.parseDouble(strNum);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }
    // Method to add two large numbers both given as String, will internally be represented as linked lists, returns calculated Sum;
    public String add(String addend1, String addend2){
        if(!isNumeric(addend1)|| !isNumeric(addend2)){
            return "please enter numeric values.";
        }

        if(addend1.length()>addend2.length()){
            addend2 = String.format("%" + addend1.length() + "s", addend2).replace(' ', '0');

        }
        else if (addend2.length()>addend1.length()){
            addend1 = String.format("%" + addend2.length() + "s", addend1).replace(' ', '0');
        }
        LinkedList addendList1 = convertLargeNumberToLinkedList(addend1);
        //printLinkList(addendList1);
        LinkedList addendList2 = convertLargeNumberToLinkedList(addend2);
        //printLinkList(addendList2);

        ListNode currentAddendNode1= addendList1.tail;
        ListNode currentAddendNode2= addendList2.tail;

        String sumAsString="";
        int carry= 0;

        while (currentAddendNode1!=null && currentAddendNode2!=null){
            int sum= currentAddendNode1.digit+currentAddendNode2.digit+carry;
            carry= sum/10;
            int currentDigit=sum%10;
            sumAsString=String.valueOf(currentDigit)+sumAsString;
            currentAddendNode1=currentAddendNode1.prev;
            currentAddendNode2=currentAddendNode2.prev;
        }

        if(carry!=0) {
            sumAsString = String.valueOf(carry) + sumAsString;
        }

        return sumAsString;
    }


    // Method to subtract the second large number from the first, both given as String, will internally be represented as linked lists, returns calculated Difference
    public String subtract(String minuend, String subtrahend){
        if(!isNumeric(minuend)|| !isNumeric(subtrahend)){
            return "please enter numeric values.";
        }

        subtrahend = String.format("%" + minuend.length() + "s", subtrahend).replace(' ', '0');

        LinkedList minuendList=convertLargeNumberToLinkedList(minuend);
        LinkedList subtrahendList=convertLargeNumberToLinkedList(subtrahend);

        ListNode currentMinuendNode=minuendList.tail;
        ListNode currentSubtrahendNode=subtrahendList.tail;

        String diffAsString="";
        int diff;
        while(currentMinuendNode!=null && currentSubtrahendNode!=null){
            if(currentMinuendNode.digit>=currentSubtrahendNode.digit){
                diff=currentMinuendNode.digit-currentSubtrahendNode.digit;
                diffAsString=String.valueOf(diff)+diffAsString;
                currentMinuendNode=currentMinuendNode.prev;
                currentSubtrahendNode=currentSubtrahendNode.prev;
            }
            else if (currentSubtrahendNode.digit>=currentMinuendNode.digit){
                diff=(10+currentMinuendNode.digit)-currentSubtrahendNode.digit;
                diffAsString=String.valueOf(diff)+diffAsString;
                currentMinuendNode.prev.digit--;
                currentMinuendNode=currentMinuendNode.prev;
                currentSubtrahendNode=currentSubtrahendNode.prev;

            }

        }

        return diffAsString;

    }





    private String multiplyWithOneDigit(String multiplicand, int multiplier){
        if(!isNumeric(multiplicand)){
            return "please enter numeric values.";
        }

        LinkedList multiplicandList=convertLargeNumberToLinkedList(multiplicand);
        ListNode currentMultiplicandNode=multiplicandList.tail;

        String product="";
        int carry=0;
        int digitProduct;
        int digit;

        while (currentMultiplicandNode!=null){
            digitProduct=currentMultiplicandNode.digit*multiplier+carry;
            carry=digitProduct/10;
            digit=digitProduct%10;
            product=String.valueOf(digit)+product;
            currentMultiplicandNode=currentMultiplicandNode.prev;

        }

        if(carry!=0){
            product=String.valueOf(carry)+product;

        }
        return product;
    }



    // Method to multiply two large numbers both given as String, will internally be represented as linked lists, returns calculated Product
    public String multiply(String multiplicand, String multiplier){
        if(!isNumeric(multiplicand)|| !isNumeric(multiplier)){
            return "please enter numeric values.";
        }

        LinkedList multiplierList=convertLargeNumberToLinkedList(multiplier);
        ListNode currentMultiplierNode=multiplierList.tail;

        String sum="0";
        String product="";
        String zeros="";

        while (currentMultiplierNode!=null){
            product=multiplyWithOneDigit(multiplicand,currentMultiplierNode.digit)+zeros;
            sum=add(product,sum);
            if(currentMultiplierNode.prev==null){
                break;
            }
            currentMultiplierNode=currentMultiplierNode.prev;
            zeros=zeros+"0";
        }

        return sum;
    }

    // Method to divide the first large number by the second, dividend given as String and will internally be represented as linked lists, second number is integer, returns calculated Division
    public String divide(String divident, int divisor){
        if(!isNumeric(divident)|| divisor==0){
            return "please enter numeric values and non-zero divisor.";
        }

        LinkedList dividentList=convertLargeNumberToLinkedList(divident);

        String quotient="";
        int remainder=0;

        for (ListNode currrentNode=dividentList.head; currrentNode!=null; currrentNode=currrentNode.next){
            int currentDigit=currrentNode.digit+remainder*10;
            int currentQuotientDigit=currentDigit/divisor;
            remainder=currentDigit%divisor;
            quotient+=String.valueOf(currentQuotientDigit);
        }
        quotient=removeZero(quotient);

        if (quotient.isEmpty()){
            quotient="0";
        }

        return quotient;
    }

    public static String removeZero(String str){
        int i=0;
        while (i<str.length() && str.charAt(i)=='0')
            i++;


        StringBuffer sb=new StringBuffer(str);

        sb.replace(0,i,"");

        return sb.toString();
    }

    // Helper method to convert a string representing a large number into a linked list, returns LinkList
    public LinkedList convertLargeNumberToLinkedList(String number){
        ListNode headNote, tailNode,currentNode,newNode;
        headNote=new ListNode(Character.getNumericValue(number.charAt(0)));
        if(number.length()==1){
            tailNode=headNote;
        }
        else{
            tailNode=new ListNode(Character.getNumericValue(number.charAt(number.length()-1)));
        }

        currentNode=headNote;
        for(int i=1;i<=number.length()-2;i++){//5247
            int digit=Character.getNumericValue(number.charAt(i));
            newNode=new ListNode(digit);
            currentNode.next=newNode;
            newNode.prev=currentNode;
            currentNode=newNode;

        }
        if(number.length()!=1){
            currentNode.next=tailNode;
            tailNode.prev=currentNode;
        }
        LinkedList ll=new LinkedList(headNote,tailNode);
        return ll;

    }


}
