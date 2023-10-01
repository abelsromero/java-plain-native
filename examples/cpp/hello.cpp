#include <iostream>

int main(int argc, char **argv) {
    if (argc == 1) {
        std::cout << "Hello World!\n";
    } else {
        std::cout << "Hello " << argv[1] << "!\n";
    }
    return 0;
}
