//import java.util.Scanner;
//import java.util.regex.Matcher;
//import java.util.regex.Pattern;
//
//public class HumanCommandParser {
//    private String regex = "^(?<command>Roll|Go|List|Build (?<building>settlement|city|road (?<fromNodeId>(\\d+,))?)? (?<nodeId>\\d+))?$";
//    private Pattern pattern = Pattern.compile(regex);
//
//    public String[] parse(String command) {
//        Matcher matcher = pattern.matcher(command);
//        if (matcher.matches()) {
//            command = command.replace(",", "");
//            String line[] = command.split(" ");
//            if (line[0].equals("Roll")) {
//
//            } else if (line[0].equals("Go")) {
//
//            } else if (line[0].equals("List")) {
//
//            } else if (line[0].equals("Build")) {
//                if (line[1].equals("settlement")) {
//
//                } else if (line[1].equals("city")) {
//
//                } else if (line[1].equals("road")) {
//
//                }
//            }
//        }
//        else {
//            System.out.println("Usage string: Roll|Go|List|[Build [settlement [nodeId] | city [nodeId] | road [fromNodeId, toNodeId]]]");
//        }
//    }
//
//    public void commandLine(Player player) {
//        String regex = "^(?<command>Roll|Go|List|Build (?<building>settlement|city|road (?<fromNodeId>(\\d+,))?)? (?<nodeId>\\d+))?$";
//        Pattern pattern = Pattern.compile(regex);
//        boolean rolled = false;
//        Scanner scan = new Scanner(System.in);
//        System.out.println("Enter command: ");
//        while (true) {
//            String command = scan.nextLine();
//            Matcher matcher = pattern.matcher(command);
//            if (matcher.matches()) {
//                String line[] = command.split(" ");
//                if (rolled == false) {
//                    if (line[0].equals("Roll")) {
//                        turnController.makeResources(rollDice(), player);
//                        rolled = true;
//                        System.out.println();
//                    } else {
//                        System.out.println("Roll before continuing");
//                    }
//                } else {
//                    if (line[0].equals("Roll")) {
//                        System.out.println("Already rolled");
//                    } else if (line[0].equals("Go")) {
//                        break;
//                    } else if (line[0].equals("List")) {
//                        player.printCards(board);
//                    } else if (line[0].equals("Build")) {
//                        if (line[1].equals("settlement")) {
//                            int node = Integer.parseInt(line[2]);
//                            if (board.placeSettlement(player, node)) {
//                                System.out.println("Built settlement at node " + node);
//                            }
//                        } else if (line[1].equals("city")) {
//                            int node = Integer.parseInt(line[2]);
//                            if (board.upgradeCity(player, node)) {
//                                System.out.println("Upgraded city at node " + node);
//                            }
//                        } else if (line[1].equals("road")) {
//                            int start = Integer.valueOf(line[2].replace(",", ""));
//                            int end = Integer.valueOf(line[3]);
//                            if (board.placeRoad(player, start, end)) {
//                                System.out.println("Built road at (" + start + ", " + end + ")");
//                            }
//                        }
//                    }
//                }
//            }
//            else {
//                System.out.println("Usage string: Roll|Go|List|[Build [settlement [nodeId] | city [nodeId] | road [fromNodeId, toNodeId]]]");
//            }
//        }
//    }
//
//    public void commandLineSetup (Player player) {
//        String regex = "^(?<command>Roll|Go|List|Build (?<building>settlement|city|road (?<fromNodeId>(\\d+,))?)? (?<nodeId>\\d+))?$";
//        Pattern pattern = Pattern.compile(regex);
//        String command = null;
//        boolean builtSettlement = false;
//        boolean builtRoad = false;
//        Scanner scan = new Scanner(System.in);
//        System.out.println("Enter command: ");
//        while (true) {
//            command = scan.nextLine();
//            Matcher matcher = pattern.matcher(command);
//            if (matcher.matches()) {
//                String line[] = command.split(" ");
//                if (line[0].equals("Go")) {
//                    if (builtSettlement == true && builtRoad == true) {
//                        break;
//                    }
//                    else {
//                        System.out.println("Must finish building before proceeding");
//                    }
//                }
//                else if (line[0].equals("Roll")) {
//                    System.out.println("Can't roll during the first 2 turns");
//                }
//                else if (line[0].equals("List")) {
//                    player.printCards(board);
//                }
//                else if (line[0].equals("Build")) {
//                    if (line[1].equals("settlement")) {
//                        if (builtSettlement == false) {
//                            int node = Integer.parseInt(line[2]);
//                            if (board.placeSettlement(player, node)) {
//                                System.out.println("Built settlement at node " + node);
//                                builtSettlement = true;
//                                continue;
//                            }
//                        }
//                    }
//                    else if (line[1].equals("road")) {
//                        if (builtSettlement == true && builtRoad == false) {
//                            int start = Integer.valueOf(line[2].replace(",", ""));
//                            int end = Integer.valueOf(line[3]);
//                            if (board.placeRoad(player, start, end)) {
//                                System.out.println("Built road at (" + start + ", " + end + ")");
//                                builtRoad = true;
//                                continue;
//                            }
//                        }
//                    }
//                    System.out.println("Build unsuccessful");
//                }
//            }
//            else {
//                System.out.println("Usage string: Roll|Go|List|[Build [settlement [nodeId] | city [nodeId] | road [fromNodeId, toNodeId]]]");
//            }
//        }
//    }
//}
