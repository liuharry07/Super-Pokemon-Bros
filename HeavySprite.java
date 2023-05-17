public class HeavySprite extends MobileSprite {

  public HeavySprite(double left, double top, int width, int height, String image, double vx, double vy, int type) {
    super(left, top, width, height, image, vx, vy, type);
  }

  public void step(World world) {
    super.step(world);
    super.setVY(super.getVY() + 0.2);
  }
}
