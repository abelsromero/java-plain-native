use std::env;

pub fn extract_text() -> String {
    let args: Vec<String> = env::args().collect();

    args.get(1).cloned().unwrap_or_default()
}
