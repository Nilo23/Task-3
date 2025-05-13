public class ScheduleList {
        public static String[] getSchedule(String choice) {
                return switch (choice) {
                        case "1" -> new String[] { "r1(x)", "w2(x)", "c2", "w1(x)", "r1(x)", "c1" }; // S1
                        case "2" -> new String[] { "r1(x)", "w2(x)", "c2", "r1(x)", "c1" }; // S2
                        case "3" -> new String[] {
                                        "r2(x)", "w1(x)", "w1(y)", "c1", "r2(y)", "w2(x)", "w2(y)", "c2" }; // S3
                        default -> null;
                };
        }
}
