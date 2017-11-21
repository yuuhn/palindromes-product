package basepackage;

import java.util.*;

class FindProduct {
    private String inputStr;
    private int maxProduct;

    FindProduct(String str) {
        inputStr = str;
        maxProduct = findMaxProduct();
    }

    int getMaxProduct() {
        return maxProduct;
    }

    private class Palindrome {
        private int startId,
                endId;

        Palindrome(int start, int end) {
            startId = start;
            endId = end;
        }

        int getStart() {
            return startId;
        }

        int getEnd() {
            return endId;
        }

        int getLength() {
            return endId - startId + 1;
        }
    }

    private boolean isPalindrome(String str) {
        for (int i = 0; i < str.length() / 2; i++ ) {
            if (str.charAt(i) != str.charAt(str.length() - i - 1)) {
                return false;
            }
        }

        return true;
    }

    private Palindrome[] allPalindromes() {
        List<Palindrome> palindromes = new ArrayList<Palindrome>();

        for (int i = 0; i < inputStr.length() - 1; i++) {
            for (int len = inputStr.length() - i; len > 1; len--) {
                if (isPalindrome(inputStr.substring(i,i + len))) {
                    palindromes.add(new Palindrome(i, i + len - 1));
                }
            }
        }

        return palindromes.toArray(new Palindrome[palindromes.size()]);
    }

    private Palindrome[] sortPalindromes(Palindrome[] palindromes) {
        int len = palindromes.length;

        for (int i = 1; i < len; i++) {
            for (int j = 1; j < len - i + 1; j++) {
                if (palindromes[j - 1].getStart() > palindromes[j].getStart()) {
                    Palindrome p = palindromes[j];
                    palindromes[j] = palindromes[j - 1];
                    palindromes[j - 1] = p;
                }
            }
        }

        return palindromes;
    }

    private int findMaxProduct() {
        int maxProduct = 1;
        Palindrome[] palindromes = allPalindromes();
        // Sort all palindromes!
        palindromes = sortPalindromes(palindromes);

        for (int i = 0; i < palindromes.length; i++) {

            // if (palindromes.length - i) * (palindromes.length - 1) < maxProduct => return maxProduct
            if ((palindromes.length - i) * (palindromes.length - i) < maxProduct) {
                return maxProduct;
            }

            // Get variables of a palindrome
            int pStart = palindromes[i].getStart();
            int pEnd = palindromes[i].getEnd();
            int pLength = palindromes[i].getLength();

            // whole sequence == palindrome => no product
            if (pLength != palindromes.length) {

                // If palindrome.length == length - 1 => product = palindrome.length
                if (pLength == palindromes.length - 1) {
                    if (maxProduct < pLength) {
                        maxProduct = pLength;
                    }
                }
                else {
                    // Check all other palindromes, which startId > endId
                    for (int j = i + 1; j < palindromes.length; j++) {
                        if (palindromes[j].getStart() > pEnd) {
                            int product = pLength * (palindromes[j].getLength());
                            if (maxProduct < product) {
                                maxProduct = product;
                            }
                        }
                    }
                }
            }
        }

        return maxProduct;
    }
}

public class Main {

    public static void main(String[] args) {
        /* Enter your code here. Read input from STDIN. Print output to STDOUT. Your class should be named Solution. */

        Scanner sc = new Scanner(System.in);
        String input = sc.next();

        /*
        String input = String.join("aacdcbdccdccbacadcdcbbbdbcaacabddbcaddbccdcaccadbadcdcdcbaabcadbdcab",
                                   "dbcccbddcdabcaadcdadcacbdbccccbcacccdbacbddbacbccdadddbccdb",
                                   "ddcabbbcccdddadddccdbddabbddcaadacaacdddbcbbccdadadbdbbcaaabccabdaaddadaa"
                                   );
        */

        FindProduct product = new FindProduct(input);
        int maxProduct = product.getMaxProduct();
        System.out.println(maxProduct);
    }
}