package main.java.exercise;

public class StableMatchingSolution {
    private StableMatchingInstance inst; // Zugehörige Instanz
    private StudentSolutionImplementation studentSolutionImplementation;

    private int[] solutionChild;         // Zuordnung Kind -> Eltern
    private int[] solutionFamily;        // Zuordnung Eltern -> Kind

    private int pointerNextChild;        // int Wert des nächsten freien Kindes

    /**
     * Erstellt ein leeres Lösungsobjekt
     * @param inst Die zugrunde liegende Instanz
     */
    public StableMatchingSolution(StableMatchingInstance inst, StudentSolutionImplementation studentSolutionImplementation) {
        this.inst = inst;

        int n = inst.getN();
        solutionChild = initArray(n);
        solutionFamily = initArray(n);
        this.studentSolutionImplementation = studentSolutionImplementation;

        pointerNextChild = 0;
    }

    private int[] initArray(int n) {
        int[] res = new int[n];
        for (int i = 0; i < n; i++) {
            res[i] = -1;
        }
        return res;
    }

    public StableMatchingInstance getInstance() {
        return this.inst;
    }

    public int getAssignedFamily(int child) {
        return solutionChild[child];
    }

    public int getAssignedChild(int family) {
        return solutionFamily[family];
    }

    public boolean isChildFree(int child) {
        return getAssignedFamily(child) == -1;
    }

    public boolean isFamilyFree(int family) {
        return getAssignedChild(family) == -1;
    }

    public boolean assign(int child, int family) {
        if (isChildFree(child) && isFamilyFree(family)) {
            solutionChild[child] = family;
            solutionFamily[family] = child;

            // Pointer ggf. hochzählen
            for (int i = pointerNextChild; i <= inst.getN(); i++) {
                if (i == inst.getN() || solutionChild[i] == -1) {
                    pointerNextChild = i;
                    break;
                }
            }

            return true;
        }
        return false;
    }

    public boolean setFree(int child) {
        if (!isChildFree(child)) {
            int family = solutionChild[child];
            solutionChild[child] = -1;
            solutionFamily[family] = -1;

            // Pointer ggf. runterzählen
            pointerNextChild = Math.min(pointerNextChild, child);

            return true;
        }
        return false;
    }

    public boolean hasUnassignedChildren() {
        return getNextUnassignedChild() >= 0;
    }

    public int getNextUnassignedChild() {
        if (pointerNextChild < getN()) {
            return pointerNextChild;
        }
        return -1;
    }

    public int getN() {
        return this.inst.getN();
    }

    // Vertausche Zuordnungen der Kinder i und j
    public void swap(int i, int j) {
        int familyI = this.getAssignedFamily(i);
        int familyJ = this.getAssignedFamily(j);

        setFree(i);
        setFree(j);

        assign(i, familyJ);
        assign(j, familyI);
    }

    private String formatRight(String s, int digits) {
        StringBuilder sb = new StringBuilder();
        for (int i = s.length(); i < digits; i++) {
            sb.append(" ");
        }
        sb.append(s);
        return sb.toString();
    }

    private String arrayToString(int[] array) {
        StringBuilder sb = new StringBuilder();
        int digits = Integer.toString(inst.getN()).length();

        for (int i = 0; i < inst.getN(); i++) {
            sb.append(" " + formatRight(Integer.toString(i), digits));
        }
        sb.append("\n");
        for (int i = 0; i < array.length; i++) {
            String s = Integer.toString(array[i]);
            if (array[i] == -1) {
                s = "";
            }
            sb.append(" " + formatRight(s, digits));
        }
        return sb.toString();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("Assignment of Children:\n");
        sb.append(arrayToString(solutionChild));

        sb.append("\n");
        sb.append("Assignment of Families:\n");
        sb.append(arrayToString(solutionFamily));

        sb.append("\n");
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
        StableMatchingSolution sol = (StableMatchingSolution) obj;

        if (!this.getInstance().equals(sol.getInstance())) {
            return false;
        }

        for (int i = 0; i < this.getInstance().getN(); i++) {
            if (this.solutionFamily[i] != sol.solutionFamily[i] || this.solutionChild[i] != sol.solutionChild[i]) {
                return false;
            }
        }

        return true;
    }
}

