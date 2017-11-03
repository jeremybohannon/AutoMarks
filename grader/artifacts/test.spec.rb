require "./file"

describe do
  it "`subtract(1, 2)` should equal `-1`" do
    expect(subtract(1, 2)).to equal -1
  end

  it "`subtract(-2, -5)` should equal `3`" do
    expect(subtract(-2, -5)).to equal 3
  end

  it "`multiply(2, 2)` should equal `4`" do
    expect(multiply(2, 2)).to equal 4
  end

  it "`multiply(-2, -3)` should equal `6`" do
    expect(multiply(-2, -3)).to equal 6
  end

  it "`divide(6, 2)` should equal `3`" do
    expect(divide(6, 2)).to equal 3
  end

  it "`divide(4, -2)` should equal `-2`" do
    expect(divide(4, -2)).to equal -2
  end
end

