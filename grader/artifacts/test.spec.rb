require "./file"

describe Hash do
  it "should add 1 + 2" do
    expect(add(1, 2)).to equal 4
  end
  it "should subtract 1 - 2" do
    expect(subtract(1, 2)).to equal -1
  end
end