use std::env;

pub fn extract_text() -> String {
    let args: Vec<String> = env::args().collect();

    if args.len() == 1 {
        return String::new();
    } else {
        return String::from(&args[1]);
    }
}
