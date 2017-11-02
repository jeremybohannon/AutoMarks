FROM ruby

RUN gem install rspec rspec_junit_formatter uuidtools

COPY . .

RUN chmod +x ./input.sh

ENTRYPOINT [ "./input.sh" ]