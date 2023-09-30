use hello::extract_text;

fn main() {
    let text = extract_text();

    if text.is_empty() {
        println!("Hello World!");
    } else {
        println!("Hello {}!", text);
    }
}
