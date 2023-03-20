package main.java.exercise;

public class StableMatchingInstance {
    private int n;                      // Anzahl der Kinder / Eltern
    private StudentSolutionImplementation studentSolutionImplementation;
    
    private int[][] preferencesChild;   // Präferenzmatrix aus Sicht der Kinder
    private int[][] preferencesFamily;  // Präferenzmatrix aus Sicht der Eltern

    public StableMatchingInstance(int n, int[][] preferencesChild, int[][] preferencesFamily, StudentSolutionImplementation studentSolutionImplementation) {
        this.n = n;
        this.preferencesChild = preferencesChild;
        this.preferencesFamily = preferencesFamily;
        this.studentSolutionImplementation = studentSolutionImplementation;
    }

    public int getN() {
        return n;
    }

    public int getFamilyOfChildAtRank(int child, int rank) {
        return preferencesChild[child][rank];
    }

    public int getChildOfFamilyAtRank(int family, int rank) {
        return preferencesFamily[family][rank];
    }

    private int findIndex(int[] numbers, int element) {
        return this.studentSolutionImplementation.findIndex(numbers, element);
    }

    public int getRankOfFamilyForChild(int child, int family) {
        int[] temp = preferencesChild[child];
        return(findIndex(temp, family));
    }

    public int getRankOfChildForFamily(int family, int child) {
        int[] temp = preferencesFamily[family];
        return(findIndex(temp, child));
    }



    private String formatRight(int number, int digits) {
        StringBuilder sb = new StringBuilder();
        String s = Integer.toString(number);
        for (int i = s.length(); i < digits; i++) {
            sb.append(" ");
        }
        sb.append(s);
        return sb.toString();
    }

    private String matrixToString(int[][] matrix) {
        StringBuilder sb = new StringBuilder();
        int digits = Integer.toString(n).length();
        for (int i = 0; i < digits; i++) {
            sb.append(" ");
        }
        for (int i = 0; i < n; i++) {
            sb.append(" " + formatRight(i, digits));
        }

        for (int i = 0; i < matrix.length; i++) {
            sb.append("\n");
            sb.append(formatRight(i, digits));
            for (int j = 0; j < matrix[i].length; j++) {
                sb.append(" " + formatRight(matrix[i][j], digits));
            }
        }
        return sb.toString();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        if (preferencesChild != null) {
            sb.append("Preferences of Children:\n");
            sb.append(matrixToString(preferencesChild));
        }
        else {
            sb.append("Preferences of Children: ??");
        }
        sb.append("\n");
        if (preferencesFamily != null) {
            sb.append("Preferences of Parents:\n");
            sb.append(matrixToString(preferencesFamily));
        }
        else {
            sb.append("Preferences of Parents: ??");
        }
        return sb.toString();
    }

    public void print() {
        System.out.println(this.toString());
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (this.getClass() != obj.getClass())
            return false;
        StableMatchingInstance inst = (StableMatchingInstance) obj;

        if (this.n != inst.n) {
            return false;
        }

        for (int i = 0; i < this.n; i++) {
            for (int j = 0; j < this.n; j++) {
                if (this.preferencesChild[i][j] != inst.preferencesChild[i][j] || this.preferencesFamily[i][j] != inst.preferencesFamily[i][j]) {
                    return false;
                }
            }
        }

        return true;
    }

}
