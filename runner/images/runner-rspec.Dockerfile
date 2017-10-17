# automarks/runner-rspec

FROM 1and1internet/ubuntu-16-rspec

RUN gem install rspec rspec_junit_formatter

CMD bash