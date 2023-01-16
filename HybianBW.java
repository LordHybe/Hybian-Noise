import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.util.Random;

public class MyProgram
{
    //inits shading characters
    public static String[] shadingSymbols = {" ", " ", "░", "░", "▒", "▒", "▓", "▓", "█", "█"};
    
    public static int xPlane = 50;
    public static int yPlane = 100;
    public static Integer[][] noiseData = new Integer[xPlane][yPlane];
    public static String[][] noiseGrid = new String[xPlane][yPlane];
    
    public static boolean checkForNull(){
        for(int i = 0; i < xPlane; i++){
            for(int j = 0; j < yPlane; j++){
                if(noiseGrid[i][j] == null) return true;
            }
        }
        return false; //it will reach here if return true was not called.
    }
    
    public static void main(String[] args)
    {
        Scanner input = new Scanner(System.in);
        
        System.out.print("Enter Seed (do not enter any value for random seed): ");
        String stringSeed = input.nextLine();
        long seed = 0;
        
        //converts user entered seed into long value
        if(stringSeed.isEmpty()){
            seed = (long) (Math.random() * 10000000000L);
            System.out.println("Generating seed: " + seed + "\n");
        }
        else if(stringSeed.matches("^[0-9]+$")){
            seed = Long.parseLong(stringSeed);
            System.out.println("Generating seed: " + seed + "\n");
        }
        else{
            for(char c : stringSeed.toCharArray()){
                seed = seed * 26 + (c - 'A');
            }
            System.out.println("Generating seed: " + stringSeed + "\n");
        }
        
        //chooses random # for each point, then loops through every point at random (checking off each point afterward) assigning it a random value based on its naighbors
        Random rngSeed = new Random(seed);
        
        //inits random points
        for(int i = 0; i < xPlane; i++){
            for(int j = 0; j < yPlane; j++){
                noiseData[i][j] = rngSeed.nextInt(shadingSymbols.length);
            }
        }
        
        //loops through every point assigning new value for edges at random (excluding corners)
        ArrayList<Integer> usedNums = new ArrayList<Integer>();
        
        //left edge
        while(usedNums.size() != xPlane - 2){
            int coord = 1 + rngSeed.nextInt(xPlane - 2);
            if(!usedNums.contains(coord) && coord != 0){
                usedNums.add(coord);
                noiseData[coord][0] += noiseData[coord+ 1][0] + noiseData[coord- 1][0] + noiseData[coord][1];
                noiseData[coord][0] /= 4;
                
                //converts numbers in data into characters in grid
                noiseGrid[coord][0] = shadingSymbols[noiseData[coord][0]];
            }
        }
        
        //right edge
        usedNums.clear();
        while(usedNums.size() != xPlane - 2){
            int coord = 1 + rngSeed.nextInt(xPlane - 2);
            if(!usedNums.contains(coord) && coord != 0){
                usedNums.add(coord);
                noiseData[coord][yPlane- 1] += noiseData[coord+ 1][yPlane- 1] + noiseData[coord- 1][yPlane- 1] + noiseData[coord][yPlane- 2];
                noiseData[coord][yPlane- 1] /= 4;
                
                //converts numbers in data into characters in grid
                noiseGrid[coord][yPlane- 1] = shadingSymbols[noiseData[coord][yPlane- 1]];
            }
        }
        
        //bottom edge
        usedNums.clear();
        while(usedNums.size() != yPlane - 2){
            int coord = 1 + rngSeed.nextInt(yPlane - 2);
            if(!usedNums.contains(coord) && coord != 0){
                usedNums.add(coord);
                noiseData[xPlane- 1][coord] += noiseData[xPlane- 1][coord+ 1] + noiseData[xPlane- 1][coord- 1] + noiseData[xPlane- 2][coord];
                noiseData[xPlane- 1][coord] /= 4;
                
                //converts numbers in data into characters in grid
                noiseGrid[xPlane- 1][coord] = shadingSymbols[noiseData[xPlane- 1][coord]];
            }
        }
        
        //top edge
        usedNums.clear();
        while(usedNums.size() != yPlane - 2){
            int coord = 1 + rngSeed.nextInt(yPlane - 2);
            if(!usedNums.contains(coord) && coord != 0){
                usedNums.add(coord);
                noiseData[0][coord] += noiseData[0][coord+ 1] + noiseData[0][coord- 1] + noiseData[1][coord];
                noiseData[0][coord] /= 4;
                
                //converts numbers in data into characters in grid
                noiseGrid[0][coord] = shadingSymbols[noiseData[0][coord]];
            }
        }
        
        //corners
        noiseData[0][0] = rngSeed.nextInt(shadingSymbols.length);
        
        noiseGrid[0][0] = shadingSymbols[noiseData[0][0]];
        noiseGrid[xPlane- 1][0] = shadingSymbols[noiseData[xPlane- 1][0]];
        noiseGrid[0][yPlane- 1] = shadingSymbols[noiseData[0][xPlane- 1]];
        noiseGrid[xPlane- 1][yPlane- 1] = shadingSymbols[noiseData[xPlane- 1][yPlane- 1]];
        
        //entire middle section
        ArrayList<Integer> usedNums2 = new ArrayList<Integer>();
        int loopIteration = 0;
        
        while(checkForNull()){
            usedNums.clear();
            usedNums2.clear();
            
            while(usedNums.size() != xPlane - 2){
                int xCoord = 1 + rngSeed.nextInt(xPlane - 2);;
                
                if(!usedNums.contains(xCoord) && xCoord != 0){
                    int yCoord = 1 + rngSeed.nextInt(yPlane - 2);;
                    
                    while(usedNums2.contains(yCoord) || yCoord == 0){
                        yCoord = 1 + rngSeed.nextInt(yPlane - 2);;
                    }
                    
                    noiseData[xCoord][yCoord] += noiseData[xCoord][yCoord+ 1] + noiseData[xCoord][yCoord- 1] + noiseData[xCoord+ 1][yCoord] + noiseData[xCoord- 1][yCoord];  
                    noiseData[xCoord][yCoord] /= 5;
                    
                    if(noiseData[xCoord][yCoord] > 1 && noiseData[xCoord][yCoord] < 7){
                        int random = (int) Math.floor(rngSeed.nextDouble() * 4.5) - 1;
                        if (rngSeed.nextDouble() < 0.6) random = 0;
                        
                        noiseData[xCoord][yCoord] += random;
                    }
                    
                    //converts numbers in data into characters in grid
                    noiseGrid[xCoord][yCoord] = shadingSymbols[noiseData[xCoord][yCoord]];
                    
                    usedNums2.add(yCoord);
                    usedNums.add(xCoord);
                }
            }
            loopIteration++;
        }
        
        //prints out graphGrid
        for (String[] x : noiseGrid){
            for (String y : x){
                if(y != null){
                    System.out.print(y);
                }
                else{
                    System.out.print("0");
                }
            }
            System.out.println();
        }
        
        System.out.println("\nLooped through " + loopIteration + " times.");
    }
}