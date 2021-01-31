use std::{fs::File, io::BufReader};
use std::{io::BufRead, str::FromStr};

fn main() {
    let input = File::open("../input").unwrap();
    let lines = BufReader::new(input).lines();
    let iterator = lines.into_iter().filter_map(|line| line.ok());
    let result = iterator
        .map(map_input_to_password)
        .filter(|(rule, s)| rule.is_valid(s))
        .count();
    println!("Count: {}", result);
}

fn map_input_to_password(s: String) -> (PasswordRule, String) {
    let splitted: Vec<&str> = s.split(':').collect();
    let password_rule: PasswordRule = splitted[0].parse().unwrap();
    (password_rule, splitted[1].trim().to_string())
}

struct PasswordRule {
    from: usize,
    to: usize,
    letter: char,
}

impl PasswordRule {
    fn is_valid(&self, s: &str) -> bool {
        let count = s.chars().filter(|c| c == &self.letter).count();
        self.from <= count && count <= self.to
    }
}

impl FromStr for PasswordRule {
    type Err = ();
    fn from_str(s: &str) -> Result<Self, <Self as FromStr>::Err> {
        let splitted: Vec<&str> = s.split(|c| c == '-' || c == ' ').collect();
        let from: usize = splitted[0].parse().unwrap();
        let to: usize = splitted[1].parse().unwrap();
        let letter: char = splitted[2].chars().next().unwrap();
        Ok(PasswordRule { from, to, letter })
    }
}
