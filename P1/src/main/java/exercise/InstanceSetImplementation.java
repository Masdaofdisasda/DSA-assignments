package main.java.exercise;

import main.java.framework.InstanceSet;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class InstanceSetImplementation extends InstanceSet<InstanceImplementation, StudentSolutionImplementation, ResultImplementation, VerifierImplementation, Integer[]> {

    private int[] sizesFindIndex;
    private int[] sizesStableMatching;

    private boolean boolFindIndex;
    private boolean boolStableMatching;

    private StableMatchingInstance[][] stableMatchingInstances;  // Nr + size
    private int[][] findIndexNumbers;                            // size + Zahlen

    public InstanceSetImplementation(Path instanceSetPath, Path outputPath) {
        super(instanceSetPath, outputPath, ResultImplementation.class);
    }

    @Override
    protected InstanceImplementation instanceFromCsv(String line) {
        String[] splitLine = line.split(",");
        String problemType = splitLine[1];
        if (problemType.equals("Indexsuche")) {
            if (!boolFindIndex) {
                System.out.println("Read in Find Index Instances");
                sizesFindIndex = readSizesFindIndex();
                readFindNumberProblems();
                boolFindIndex = true;
            }
            int size = Integer.parseInt(splitLine[2]);

            int[] zahlen = null;
            for (int i = 0; i < findIndexNumbers.length; i++) {
                if (findIndexNumbers[i].length == size) {
                    zahlen = findIndexNumbers[i];
                }
            }

            int element = Integer.parseInt(splitLine[3]);
            int solutionIndex = Integer.parseInt(splitLine[4]);
            return new InstanceImplementation(splitLine[1], Integer.parseInt(splitLine[0]), zahlen, element, solutionIndex, null, null, null, false, null);
        } else if (problemType.equals("Gale Shapley")) {
            if (!boolStableMatching) {
                System.out.println("Read in Stable Matching Instances");
                sizesStableMatching = readSizesStableMatching();
                readStableMatchingProblems();
                boolStableMatching = true;
            }
            int size = Integer.parseInt(splitLine[2]);
            int nr = Integer.parseInt(splitLine[3]);

            StableMatchingInstance instance = null;
            for (int i = 0; i < stableMatchingInstances[nr].length; i++) {
                if (stableMatchingInstances[nr][i].getN() == size) {
                    instance = stableMatchingInstances[nr][i];
                }
            }

            StableMatchingSolution solGaleShapley = new StableMatchingSolution(instance, this.provideStudentSolution());
            String[] solGaleShapleyString = splitLine[4].split(" ");
            for (int i = 0; i < solGaleShapleyString.length; i++) {
                int f = Integer.parseInt(solGaleShapleyString[i]);
                solGaleShapley.assign(i, f);
            }

            return new InstanceImplementation(splitLine[1], Integer.parseInt(splitLine[0]), null, -1, -1, instance, solGaleShapley, null, false, null);
        } else if (problemType.equals("Stable Matching Positiv") || problemType.equals("Stable Matching Negativ")) {
            if (!boolStableMatching) {
                System.out.println("Read in Stable Matching Instances");
                sizesStableMatching = readSizesStableMatching();
                readStableMatchingProblems();
                boolStableMatching = true;
            }
            int size = Integer.parseInt(splitLine[2]);
            int nr = Integer.parseInt(splitLine[3]);

            StableMatchingInstance instance = null;
            for (int i = 0; i < stableMatchingInstances[nr].length; i++) {
                if (stableMatchingInstances[nr][i].getN() == size) {
                    instance = stableMatchingInstances[nr][i];
                }
            }

            StableMatchingSolution solutionStableCandit = new StableMatchingSolution(instance, this.provideStudentSolution());
            String[] solutionStableCanditString = splitLine[4].split(" ");
            for (int i = 0; i < solutionStableCanditString.length; i++) {
                int f = Integer.parseInt(solutionStableCanditString[i]);
                solutionStableCandit.assign(i, f);
            }

            boolean solutionStable = Boolean.parseBoolean(splitLine[5]);

            return new InstanceImplementation(splitLine[1], Integer.parseInt(splitLine[0]), null, -1, -1, instance, null, solutionStableCandit, solutionStable, null);
        } else {
            if (!boolStableMatching) {
                System.out.println("Read in Stable Matching Instances");
                sizesStableMatching = readSizesStableMatching();
                readStableMatchingProblems();
                boolStableMatching = true;
            }
            int size = Integer.parseInt(splitLine[2]);
            int nr = Integer.parseInt(splitLine[3]);

            StableMatchingInstance instance = null;
            for (int i = 0; i < stableMatchingInstances[nr].length; i++) {
                if (stableMatchingInstances[nr][i].getN() == size) {
                    instance = stableMatchingInstances[nr][i];
                }
            }

            StableMatchingSolution solBruteForce = new StableMatchingSolution(instance, this.provideStudentSolution());
            String[] solBruteForceString = splitLine[4].split(" ");
            for (int i = 0; i < solBruteForceString.length; i++) {
                int f = Integer.parseInt(solBruteForceString[i]);
                solBruteForce.assign(i, f);
            }

            return new InstanceImplementation(splitLine[1], Integer.parseInt(splitLine[0]), null, -1, -1, instance, null, null, false, solBruteForce);
        }
    }

    @Override
    protected StudentSolutionImplementation provideStudentSolution() {
        return new StudentSolutionImplementation();
    }

    @Override
    protected VerifierImplementation provideVerifier() {
        return new VerifierImplementation();
    }


    protected int[] readSizesFindIndex() {
        Path pfad = FileSystems.getDefault().getPath("additional-input", "sizesIndexsuche.csv");
        BufferedReader br;
        try {
            br = Files.newBufferedReader(pfad);
            return(this.parseNumbers(br));
        } catch (IOException e) {
            System.err.println("Error while reading additional input");
        }
        return null;
    }

    protected int[] readSizesStableMatching() {
        Path pfad = FileSystems.getDefault().getPath("additional-input", "sizesStableMatching.csv");
        BufferedReader br;
        try {
            br = Files.newBufferedReader(pfad);
            return(this.parseNumbers(br));
        } catch (IOException e) {
            System.err.println("Error while reading additional input");
        }
        return null;
    }


    protected int[] parseNumbers(BufferedReader reader) {
        List<Integer> numbers = new ArrayList<Integer>();
        try {
            String line;
            while ((line = reader.readLine()) != null) {
                numbers.add(Integer.parseInt(line));
            }
        } catch (IOException e) {
            return null;
        }
        int[] res = new int[numbers.size()];
        for (int i = 0; i < res.length; i++) {
            res[i] = numbers.remove(0);
        }
        return res;
    }


    protected int[] parseFindIndex(BufferedReader reader) {
        List<Integer> numbers = new ArrayList<Integer>();
        try {
            String line;
            while ((line = reader.readLine()) != null) {
                String temp = line.split(",")[1];
                numbers.add(Integer.parseInt(temp));
            }
        } catch (IOException e) {
            return null;
        }
        int[] res = new int[numbers.size()];
        for (int i = 0; i < res.length; i++) {
            res[i] = numbers.remove(0);
        }
        return res;
    }

    public void readFindNumberProblems() {
        int[] neueStellen = null;

        Path pfad = FileSystems.getDefault().getPath("additional-input", "additionalInfoIndexsuche.csv");
        BufferedReader br;
        try {
            br = Files.newBufferedReader(pfad);
            neueStellen = this.parseFindIndex(br);
        } catch (IOException e) {
            System.err.println("Error while reading additional-input");
        }

        int maxsize = sizesFindIndex[sizesFindIndex.length - 1];
        this.findIndexNumbers = new int[sizesFindIndex.length][];

        int[] zahlen = new int[1];
        int pointer = 0;

        for (int n = 2; n <= maxsize; n++) {
            int neueStelle = neueStellen[n-2];

            int[] zahlenNeu = new int[n];

            // Kopieren
            // Schritt 1: Alles vor neuer Stelle kopieren
            for (int j = 0; j < neueStelle; j++) {
                zahlenNeu[j] = zahlen[j];
            }

            // Schritt 2: n-1 an Stelle einfügen
            zahlenNeu[neueStelle] = n - 1;

            // Schritt 3: Alles nach neuer Stelle um 1 shiften
            for (int j = neueStelle; j < n - 1; j++) {
                zahlenNeu[j + 1] = zahlen[j];
            }

            if (sizesFindIndex[pointer] == n) {
                this.findIndexNumbers[pointer] = zahlenNeu;
                pointer += 1;
            }

            zahlen = zahlenNeu;
        }
    }


    public void readStableMatchingProblems() {
        int anz = -1;

        Path pfad = FileSystems.getDefault().getPath("additional-input", "additionalInfoStableMatching.csv");
        BufferedReader br;
        try {
            br = Files.newBufferedReader(pfad);
            anz = Integer.parseInt(br.readLine());

            stableMatchingInstances = new StableMatchingInstance[anz][sizesStableMatching.length];

            int[][] prefChild;
            int[][] prefFamily;

            for (int k = 0; k < anz; k++) {
                String[] splitLine = br.readLine().split(",");
                int pointer = sizesStableMatching.length - 1;  // aktuelle Instanz
                int n = sizesStableMatching[pointer];

                // Größte Instanz generieren
                String[] prefChildStrings = splitLine[2].split(" ");
                prefChild = new int[n][n];
                for (int i = 0; i < n; i++) {
                    for (int j = 0; j < n; j++) {
                        int kk = i * n + j;
                        prefChild[i][j] = Integer.parseInt(prefChildStrings[kk]);
                    }
                }

                String[] prefFamilyStrings = splitLine[3].split(" ");
                prefFamily = new int[n][n];
                for (int i = 0; i < n; i++) {
                    for (int j = 0; j < n; j++) {
                        int kk = i * n + j;
                        prefFamily[i][j] = Integer.parseInt(prefFamilyStrings[kk]);
                    }
                }

                stableMatchingInstances[k][pointer] = new StableMatchingInstance(n, prefChild, prefFamily, provideStudentSolution());

                for (pointer = sizesStableMatching.length - 2; pointer >= 0; pointer--) {
                    n = sizesStableMatching[pointer];
                    StableMatchingInstance inst = stableMatchingInstances[k][pointer + 1];

                    // Instanzen werden aus nächst größerer durch Streichung erzeugt.
                    prefChild = new int[sizesStableMatching[pointer]][sizesStableMatching[pointer]];
                    for (int i = 0; i < n; i++) {
                        int index = 0;
                        for (int j = 0; j < inst.getN(); j++) {
                            if (inst.getFamilyOfChildAtRank(i, j) < n) {
                                prefChild[i][index] = inst.getFamilyOfChildAtRank(i, j);
                                index += 1;
                            }
                        }
                    }

                    prefFamily = new int[sizesStableMatching[pointer]][sizesStableMatching[pointer]];
                    for (int i = 0; i < n; i++) {
                        int index = 0;
                        for (int j = 0; j < inst.getN(); j++) {
                            if (inst.getChildOfFamilyAtRank(i, j) < n) {
                                prefFamily[i][index] = inst.getChildOfFamilyAtRank(i, j);
                                index += 1;
                            }
                        }
                    }

                    stableMatchingInstances[k][pointer] = new StableMatchingInstance(n, prefChild, prefFamily, provideStudentSolution());
                }
            }
        }
        catch (IOException e) {

        }
    }

    @Override
    protected Integer[] parseAdditionalInput(BufferedReader reader) {
        return null;
    }
}
