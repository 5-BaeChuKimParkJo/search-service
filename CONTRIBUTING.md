# Contributing

Use `type(scope): imperative subject`, no gitmoji, maximum 100 characters.
Install the shared hook: `python scripts/install_git_hooks.py` (Python 3.10+).
Test it: `python -m unittest discover -s scripts -p test_check_conventions.py`.
The authoritative rule set and fixtures are in `.conventions/`; GitOps holds
the full engineering guide in `docs/engineering/conventions.md`.

Use domain/application/adapter boundaries. Kafka envelopes, Feign clients and
Elasticsearch implementations belong to adapters, not application ports.
Java classes use PascalCase and methods camelCase. Preserve current indices,
events and HTTP contracts. Run `./gradlew clean test bootJar` with Java 17.
Never commit credentials or generated output.
