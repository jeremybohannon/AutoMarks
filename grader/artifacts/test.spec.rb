require "./file"

describe Hash do
  it "should add `1 + 2`" do
    expect(add(1, 2)).to equal 3
  end
  it "should add -4 + -2" do
    expect(add(-4, -2)).to equal -6
  end
  it "should subtract 1 - 2" do
    expect(subtract(1, 2)).to equal -1
  end
  it "should subtract -2 - -5" do
    expect(subtract(-2, -5)).to equal 3
  end
  it "should multiply 2 * 2" do
    expect(multiply(2, 2)).to equal 4
  end
  it "should multiply -2 * -3" do
    expect(multiply(-2, -3)).to equal 6
  end
  it "should divide 1 / 2" do
    expect(divide(1.0, 2)).to equal 0.5
  end
  it "should divide 4 / -2" do
    expect(divide(4, -2)).to equal -2
  end
end

